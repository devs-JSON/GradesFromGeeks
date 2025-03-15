using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;

namespace VideoToTextApi.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class VideotoAudioController : ControllerBase
    {
        private readonly string _ffmpegPath = @"C:\Users\Fatma\Downloads\ffmpeg\ffmpeg\bin\ffmpeg.exe";
        private readonly string _uploadsFolder = @"C:\Users\Fatma\Downloads\";

        [HttpPost("extract-audio")]
        public async Task<IActionResult> ExtractAudio(IFormFile videoFile)
        {
            if (videoFile == null || videoFile.Length == 0)
            {
                return BadRequest("Please upload a video file.");
            }

            try
            {
                // Save the uploaded video file
                var videoFileName = $"{Guid.NewGuid()}{Path.GetExtension(videoFile.FileName)}";
                var videoFilePath = Path.Combine(_uploadsFolder, videoFileName);

                using (var stream = new FileStream(videoFilePath, FileMode.Create))
                {
                    await videoFile.CopyToAsync(stream);
                }

                // Define the output audio file path
                var audioFileName = Path.ChangeExtension(videoFileName, ".mp3");
                var audioFilePath = Path.Combine(_uploadsFolder, audioFileName);

                // Run FFmpeg to extract audio
                var process = new Process
                {
                    StartInfo = new ProcessStartInfo
                    {
                        FileName = _ffmpegPath,
                        Arguments = $"-i \"{videoFilePath}\" -q:a 0 -map a \"{audioFilePath}\"",
                        RedirectStandardOutput = true,
                        RedirectStandardError = true,
                        UseShellExecute = false,
                        CreateNoWindow = true
                    }
                };

                process.Start();
                string output = await process.StandardError.ReadToEndAsync();
                await process.WaitForExitAsync();

                // Check if audio file was created
                if (!System.IO.File.Exists(audioFilePath))
                {
                    return StatusCode(500, "Failed to extract audio.");
                }

                return Ok(new { message = "Audio extracted successfully.", audioFilePath });
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error: {ex.Message}");
            }
        }
    }

}

