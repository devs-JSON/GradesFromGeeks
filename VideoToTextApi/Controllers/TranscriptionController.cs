using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Diagnostics;
using System.IO;
using System.Net.Http;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using VideoToTextApi.Data;
using VideoToTextApi.Models;

[Route("api/[controller]")]
[ApiController]
public class TranscriptionController : ControllerBase
{
    private readonly JSONDbContext _context;
    private readonly string _ffmpegPath = @"C:\Users\Fatma\Downloads\ffmpeg\ffmpeg\bin\ffmpeg.exe";

    public TranscriptionController(JSONDbContext context)
    {
        _context = context;
    }
    [HttpGet("VideoUrl/{id}")]
    public ActionResult<string> GetVideoUrl(int id)
    {
        var video = _context.Videos.Find(id);
        if (video == null || string.IsNullOrEmpty(video.VideoUrl))
        {
            return NotFound("Video not found.");
        }

        // Extract Google Drive file ID
        string fileId = ExtractGoogleDriveFileId(video.VideoUrl);
        if (string.IsNullOrEmpty(fileId))
        {
            return BadRequest("Invalid Google Drive URL format.");
        }

        // Generate a direct download link that LOOKS like an MP4 file
        string formattedUrl = $"https://drive.google.com/uc?export=download&id={fileId}&filename=video.mp4";

        return Ok(formattedUrl);
    }

    [HttpGet("StreamVideo/{id}")]
    public async Task<IActionResult> StreamVideo(int id)
    {
        var video = await _context.Videos.FindAsync(id);
        if (video == null || string.IsNullOrEmpty(video.VideoUrl))
        {
            return NotFound("Video not found.");
        }

        try
        {
            string fileId = ExtractGoogleDriveFileId(video.VideoUrl);
            if (string.IsNullOrEmpty(fileId))
            {
                return BadRequest("Invalid Google Drive URL format.");
            }

            string directDownloadUrl = $"https://drive.google.com/uc?export=download&id={fileId}";

            using (HttpClient client = new HttpClient())
            {
                var response = await client.GetAsync(directDownloadUrl);
                if (!response.IsSuccessStatusCode)
                {
                    return StatusCode((int)response.StatusCode, "Failed to fetch video.");
                }

                var stream = await response.Content.ReadAsStreamAsync();
                return File(stream, "video/mp4");
            }
        }
        catch (Exception ex)
        {
            return StatusCode(500, $"Error retrieving video: {ex.Message}");
        }
    }

    /// <summary>
    /// Extracts the Google Drive File ID from different link formats.
    /// </summary>
    private string ExtractGoogleDriveFileId(string googleDriveUrl)
    {
        var match = Regex.Match(googleDriveUrl, @"(?:drive\.google\.com\/(?:file\/d\/|open\?id=|uc\?id=))([a-zA-Z0-9_-]+)");
        return match.Success ? match.Groups[1].Value : string.Empty;
    }

    [HttpGet("GetByIdThenConvertVideoToText/{id}")]
    public async Task<IActionResult> GetByIdThenConvertVideoToText(int id)
    {
        var video = await _context.Videos.FindAsync(id);
        if (video == null)
        {
            return NotFound(new { error = "Video not found in the database." });
        }

        if (string.IsNullOrEmpty(video.VideoUrl))
        {
            return BadRequest(new { error = "Invalid video URL." });
        }

        try
        {
            string videoFilePath = Path.Combine(Path.GetTempPath(), $"{Guid.NewGuid()}.mp4");
            string audioFilePath = Path.Combine(Path.GetTempPath(), $"{Guid.NewGuid()}.wav");

            // Download the video
            await DownloadFileFromUrl(video.VideoUrl, videoFilePath);

            // Extract audio using FFmpeg
            bool success = ExtractAudio(videoFilePath, audioFilePath);
            if (!success)
            {
                return StatusCode(500, new { error = "Failed to extract audio from video." });
            }

            // Convert extracted audio to text
            string transcript = ConvertAudioToText(audioFilePath);

            // Cleanup: Delete temporary files
            System.IO.File.Delete(videoFilePath);
            System.IO.File.Delete(audioFilePath);

            return Ok(new { message = "Transcription successful", transcript });
        }
        catch (Exception ex)
        {
            return StatusCode(500, new { error = $"Error: {ex.Message}" });
        }
    }

    // Download video from URL, including Google Drive support
    private async Task DownloadFileFromUrl(string fileUrl, string savePath)
    {
        using (HttpClient client = new HttpClient())
        {
            if (fileUrl.Contains("drive.google.com"))
            {
                var match = Regex.Match(fileUrl, @"(?:id=|\/d\/)([\w-]+)");
                if (match.Success)
                {
                    string fileId = match.Groups[1].Value;
                    fileUrl = $"https://drive.google.com/uc?export=download&id={fileId}";
                }
            }

            var response = await client.GetAsync(fileUrl);
            response.EnsureSuccessStatusCode();

            using (var fs = new FileStream(savePath, FileMode.Create))
            {
                await response.Content.CopyToAsync(fs);
            }
        }
    }

    // Extract audio using FFmpeg
    private bool ExtractAudio(string videoFilePath, string audioFilePath)
    {
        try
        {
            var ffmpegProcess = new Process
            {
                StartInfo = new ProcessStartInfo
                {
                    FileName = _ffmpegPath,
                    Arguments = $"-y -i \"{videoFilePath}\" -ac 1 -ar 16000 \"{audioFilePath}\"",
                    RedirectStandardOutput = true,
                    RedirectStandardError = true,
                    UseShellExecute = false,
                    CreateNoWindow = true
                }
            };

            ffmpegProcess.Start();
            ffmpegProcess.StandardError.ReadToEnd(); 
            ffmpegProcess.WaitForExit();

            return System.IO.File.Exists(audioFilePath);
        }
        catch
        {
            return false;
        }
    }

    // Convert audio file to text
    private string ConvertAudioToText(string audioFilePath)
    {
        string resultText = "";

        try
        {
            using (var recognizer = new System.Speech.Recognition.SpeechRecognitionEngine())
            {
                recognizer.SetInputToWaveFile(audioFilePath);
                recognizer.LoadGrammar(new System.Speech.Recognition.DictationGrammar());

                var result = recognizer.Recognize();
                if (result != null)
                {
                    resultText = result.Text;
                }
            }
        }
        catch (Exception ex)
        {
            resultText = $"Speech recognition failed: {ex.Message}";
        }

        return resultText;
    }
}


