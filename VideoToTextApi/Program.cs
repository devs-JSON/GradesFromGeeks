
using Microsoft.Extensions.Configuration;
using VideoToTextApi.Data;
using Microsoft.EntityFrameworkCore;

namespace VideoToTextApi
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            // Add services to the container.

            builder.Services.AddControllers();
            // Learn more about configuring OpenAPI at https://aka.ms/aspnet/openapi
            builder.Services.AddOpenApi();
            builder.Services.AddHttpClient();
            builder.Services.AddSingleton<IConfiguration>(builder.Configuration);
            builder.Services.AddDbContext<JSONDbContext>(options =>
        options.UseSqlServer(builder.Configuration.GetConnectionString("JsonCon")));



            var app = builder.Build();

            // Configure the HTTP request pipeline.
            if (app.Environment.IsDevelopment())
            {
                app.MapOpenApi();
                app.UseSwaggerUI(op=>op.SwaggerEndpoint("/openapi/v1.json","v1"));
            }

            app.UseHttpsRedirection();

            app.UseAuthorization();


            app.MapControllers();

            app.Run();
        }
    }
}
