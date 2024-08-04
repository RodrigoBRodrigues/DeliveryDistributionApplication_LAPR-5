using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace DDDNetCore.Migrations
{
    public partial class changedFuncaoToRole : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "Funcao_Nome",
                schema: "dbo",
                table: "Utilizador",
                newName: "Role_Nome");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "Role_Nome",
                schema: "dbo",
                table: "Utilizador",
                newName: "Funcao_Nome");
        }
    }
}
