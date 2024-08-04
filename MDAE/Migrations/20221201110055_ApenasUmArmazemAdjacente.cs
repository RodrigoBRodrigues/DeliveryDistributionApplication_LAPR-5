using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace DDDNetCore.Migrations
{
    public partial class ApenasUmArmazemAdjacente : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Armazens_EntregasInfo_EntregaInfoId",
                schema: "dbo",
                table: "Armazens");

            migrationBuilder.DropIndex(
                name: "IX_Armazens_EntregaInfoId",
                schema: "dbo",
                table: "Armazens");

            migrationBuilder.DropColumn(
                name: "EntregaInfoId",
                schema: "dbo",
                table: "Armazens");

            migrationBuilder.AddColumn<string>(
                name: "armazemAdjacenteId",
                schema: "dbo",
                table: "EntregasInfo",
                type: "nvarchar(450)",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_EntregasInfo_armazemAdjacenteId",
                schema: "dbo",
                table: "EntregasInfo",
                column: "armazemAdjacenteId");

            migrationBuilder.AddForeignKey(
                name: "FK_EntregasInfo_Armazens_armazemAdjacenteId",
                schema: "dbo",
                table: "EntregasInfo",
                column: "armazemAdjacenteId",
                principalSchema: "dbo",
                principalTable: "Armazens",
                principalColumn: "Id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_EntregasInfo_Armazens_armazemAdjacenteId",
                schema: "dbo",
                table: "EntregasInfo");

            migrationBuilder.DropIndex(
                name: "IX_EntregasInfo_armazemAdjacenteId",
                schema: "dbo",
                table: "EntregasInfo");

            migrationBuilder.DropColumn(
                name: "armazemAdjacenteId",
                schema: "dbo",
                table: "EntregasInfo");

            migrationBuilder.AddColumn<string>(
                name: "EntregaInfoId",
                schema: "dbo",
                table: "Armazens",
                type: "nvarchar(450)",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_Armazens_EntregaInfoId",
                schema: "dbo",
                table: "Armazens",
                column: "EntregaInfoId");

            migrationBuilder.AddForeignKey(
                name: "FK_Armazens_EntregasInfo_EntregaInfoId",
                schema: "dbo",
                table: "Armazens",
                column: "EntregaInfoId",
                principalSchema: "dbo",
                principalTable: "EntregasInfo",
                principalColumn: "Id");
        }
    }
}
