using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace DDDNetCore.Migrations
{
    public partial class Utilizador1 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "Ativo_ativo",
                schema: "dbo",
                table: "Armazens",
                type: "bit",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "Utilizador",
                schema: "dbo",
                columns: table => new
                {
                    Utilizador = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    Nome_N = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    Funcao_Nome = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    Email_Nome = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    Telemovel_Nome = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    Ativo_ativo = table.Column<bool>(type: "bit", nullable: true),
                    Id = table.Column<string>(type: "nvarchar(max)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Utilizador", x => x.Utilizador);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Utilizador",
                schema: "dbo");

            migrationBuilder.DropColumn(
                name: "Ativo_ativo",
                schema: "dbo",
                table: "Armazens");
        }
    }
}
