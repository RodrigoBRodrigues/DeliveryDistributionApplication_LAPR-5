using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace DDDNetCore.Migrations
{
    public partial class EntregaInfoSGRAI : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "EntregasInfo",
                schema: "dbo",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    armazemId = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    armazemAdjacenteId = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    larguraVia = table.Column<double>(type: "float", nullable: false),
                    massa = table.Column<double>(type: "float", nullable: false),
                    raioRotunda = table.Column<double>(type: "float", nullable: false),
                    urlArmazem = table.Column<string>(type: "nvarchar(max)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_EntregasInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_EntregasInfo_Armazens_armazemAdjacenteId",
                        column: x => x.armazemAdjacenteId,
                        principalSchema: "dbo",
                        principalTable: "Armazens",
                        principalColumn: "Id");
                    table.ForeignKey(
                        name: "FK_EntregasInfo_Armazens_armazemId",
                        column: x => x.armazemId,
                        principalSchema: "dbo",
                        principalTable: "Armazens",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateIndex(
                name: "IX_EntregasInfo_armazemAdjacenteId",
                schema: "dbo",
                table: "EntregasInfo",
                column: "armazemAdjacenteId");

            migrationBuilder.CreateIndex(
                name: "IX_EntregasInfo_armazemId",
                schema: "dbo",
                table: "EntregasInfo",
                column: "armazemId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "EntregasInfo",
                schema: "dbo");
        }
    }
}
