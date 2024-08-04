using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace DDDNetCore.Migrations
{
    public partial class InitialCreate : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.EnsureSchema(
                name: "dbo");

            migrationBuilder.CreateTable(
                name: "Armazens",
                schema: "dbo",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    Designacao_Nome = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    Endereco_Rua = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    Endereco_CodigoPostal = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    Localizacao_Latitude = table.Column<double>(type: "float", nullable: true),
                    Localizacao_Longitude = table.Column<double>(type: "float", nullable: true),
                    Localizacao_Altitude = table.Column<double>(type: "float", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Armazens", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Entregas",
                schema: "dbo",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    data = table.Column<DateTime>(type: "datetime2", nullable: false),
                    armazemId = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    massa_massa = table.Column<double>(type: "float", nullable: true),
                    tempoColocar_tempo = table.Column<double>(type: "float", nullable: true),
                    tempoRetirar_tempo = table.Column<double>(type: "float", nullable: true),
                    Active = table.Column<bool>(type: "bit", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Entregas", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Entregas_Armazens_armazemId",
                        column: x => x.armazemId,
                        principalSchema: "dbo",
                        principalTable: "Armazens",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateIndex(
                name: "IX_Entregas_armazemId",
                schema: "dbo",
                table: "Entregas",
                column: "armazemId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Entregas",
                schema: "dbo");

            migrationBuilder.DropTable(
                name: "Armazens",
                schema: "dbo");
        }
    }
}
