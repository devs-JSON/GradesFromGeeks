using Microsoft.EntityFrameworkCore;
using VideoToTextApi.Models;
namespace VideoToTextApi.Data
{
    public class JSONDbContext:DbContext
    {
        public JSONDbContext(DbContextOptions<JSONDbContext> options) : base(options)
        {
        }

        public DbSet<Video> Videos { get; set; }
    }
}

