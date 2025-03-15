using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.CognitiveServices.Speech;
using System;
using System.Diagnostics;
using System.IO;
using System.Speech.Recognition;
using System.Threading.Tasks;

[Route("api/[controller]")]
[ApiController]
public class TranscriptionController : ControllerBase
{
    private readonly string _ffmpegPath = @"C:\Users\Fatma\Downloads\ffmpeg\ffmpeg\bin\ffmpeg.exe";
    private readonly string _uploadsFolder = @"C:\Users\Fatma\Downloads\";

    [HttpPost("convert-video-to-text")]
    public async Task<IActionResult> ConvertVideoToText(IFormFile videoFile)
    {
        if (videoFile == null || videoFile.Length == 0)
        {
            return BadRequest("Please upload a video file.");
        }

        try
        {
            // 🔹 Save uploaded video
            var videoFileName = $"{Guid.NewGuid()}{Path.GetExtension(videoFile.FileName)}";
            var videoFilePath = Path.Combine(_uploadsFolder, videoFileName);

            using (var stream = new FileStream(videoFilePath, FileMode.Create))
            {
                await videoFile.CopyToAsync(stream);
            }

            // 🔹 Extract audio using FFmpeg (Convert to WAV for speech recognition)
            var audioFileName = Path.ChangeExtension(videoFileName, ".wav");
            var audioFilePath = Path.Combine(_uploadsFolder, audioFileName);

            var ffmpegProcess = new Process
            {
                StartInfo = new ProcessStartInfo
                {
                    FileName = _ffmpegPath,
                    Arguments = $"-i \"{videoFilePath}\" -ac 1 -ar 16000 \"{audioFilePath}\"",
                    RedirectStandardOutput = true,
                    RedirectStandardError = true,
                    UseShellExecute = false,
                    CreateNoWindow = true
                }
            };

            ffmpegProcess.Start();
            await ffmpegProcess.StandardError.ReadToEndAsync();
            await ffmpegProcess.WaitForExitAsync();

            if (!System.IO.File.Exists(audioFilePath))
            {
                return StatusCode(500, "Failed to extract audio.");
            }

            // 🔹 Convert audio to text
            string transcript = ConvertAudioToText(audioFilePath);

            return Ok(new { message = "Transcription successful", transcript });
        }
        catch (Exception ex)
        {
            return StatusCode(500, $"Error: {ex.Message}");
        }
    }

    private string ConvertAudioToText(string audioFilePath)
    {
        string resultText = "";

        try
        {
            using (System.Speech.Recognition.SpeechRecognitionEngine recognizer = new System.Speech.Recognition.SpeechRecognitionEngine())
            {
                recognizer.SetInputToWaveFile(audioFilePath);
                recognizer.LoadGrammar(new System.Speech.Recognition.DictationGrammar()); // Load default speech recognition

                System.Speech.Recognition.RecognitionResult result = recognizer.Recognize();

                if (result != null)
                {
                    resultText = result.Text;
                }
            }
        }
        catch (Exception ex)
        {
            resultText = "Speech recognition failed: " + ex.Message;
        }

        return resultText;
    }

}
