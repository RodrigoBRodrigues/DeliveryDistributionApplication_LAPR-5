﻿// <auto-generated />
using System;
using DDDSample1.Infrastructure;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

#nullable disable

namespace DDDNetCore.Migrations
{
    [DbContext(typeof(DDDSample1DbContext))]
    [Migration("20221222135826_Utilizador1")]
    partial class Utilizador1
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "6.0.0")
                .HasAnnotation("Relational:MaxIdentifierLength", 128);

            SqlServerModelBuilderExtensions.UseIdentityColumns(modelBuilder, 1L, 1);

            modelBuilder.Entity("DDDSample1.Domain.Armazens.Armazem", b =>
                {
                    b.Property<string>("Id")
                        .HasColumnType("nvarchar(450)");

                    b.HasKey("Id");

                    b.ToTable("Armazens", "dbo");
                });

            modelBuilder.Entity("DDDSample1.Domain.Authz.Auth", b =>
                {
                    b.Property<string>("Utilizador")
                        .HasColumnType("nvarchar(450)");

                    b.Property<string>("Id")
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("Utilizador");

                    b.ToTable("Utilizador", "dbo");
                });

            modelBuilder.Entity("DDDSample1.Domain.Entregas.Entrega", b =>
                {
                    b.Property<string>("Id")
                        .HasColumnType("nvarchar(450)");

                    b.Property<string>("armazemId")
                        .HasColumnType("nvarchar(450)");

                    b.Property<DateTime>("data")
                        .HasColumnType("datetime2");

                    b.HasKey("Id");

                    b.HasIndex("armazemId");

                    b.ToTable("Entregas", "dbo");
                });

            modelBuilder.Entity("DDDSample1.Domain.EntregasInfo.EntregaInfo", b =>
                {
                    b.Property<string>("Id")
                        .HasColumnType("nvarchar(450)");

                    b.Property<string>("armazemAdjacenteId")
                        .HasColumnType("nvarchar(450)");

                    b.Property<string>("armazemId")
                        .HasColumnType("nvarchar(450)");

                    b.HasKey("Id");

                    b.HasIndex("armazemAdjacenteId");

                    b.HasIndex("armazemId");

                    b.ToTable("EntregasInfo", "dbo");
                });

            modelBuilder.Entity("DDDSample1.Domain.Armazens.Armazem", b =>
                {
                    b.OwnsOne("DDDSample1.Domain.Armazens.Ativo", "Ativo", b1 =>
                        {
                            b1.Property<string>("ArmazemId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<bool>("ativo")
                                .HasColumnType("bit");

                            b1.HasKey("ArmazemId");

                            b1.ToTable("Armazens", "dbo");

                            b1.WithOwner()
                                .HasForeignKey("ArmazemId");
                        });

                    b.OwnsOne("DDDSample1.Domain.Armazens.Designacao", "Designacao", b1 =>
                        {
                            b1.Property<string>("ArmazemId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("Nome")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("ArmazemId");

                            b1.ToTable("Armazens", "dbo");

                            b1.WithOwner()
                                .HasForeignKey("ArmazemId");
                        });

                    b.OwnsOne("DDDSample1.Domain.Armazens.Endereco", "Endereco", b1 =>
                        {
                            b1.Property<string>("ArmazemId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("CodigoPostal")
                                .HasColumnType("nvarchar(max)");

                            b1.Property<string>("Rua")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("ArmazemId");

                            b1.ToTable("Armazens", "dbo");

                            b1.WithOwner()
                                .HasForeignKey("ArmazemId");
                        });

                    b.OwnsOne("DDDSample1.Domain.Armazens.Localizacao", "Localizacao", b1 =>
                        {
                            b1.Property<string>("ArmazemId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<double>("Altitude")
                                .HasColumnType("float");

                            b1.Property<double>("Latitude")
                                .HasColumnType("float");

                            b1.Property<double>("Longitude")
                                .HasColumnType("float");

                            b1.HasKey("ArmazemId");

                            b1.ToTable("Armazens", "dbo");

                            b1.WithOwner()
                                .HasForeignKey("ArmazemId");
                        });

                    b.Navigation("Ativo");

                    b.Navigation("Designacao");

                    b.Navigation("Endereco");

                    b.Navigation("Localizacao");
                });

            modelBuilder.Entity("DDDSample1.Domain.Authz.Auth", b =>
                {
                    b.OwnsOne("DDDSample1.Domain.Authz.Ativo", "Ativo", b1 =>
                        {
                            b1.Property<string>("AuthUtilizador")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<bool>("ativo")
                                .HasColumnType("bit");

                            b1.HasKey("AuthUtilizador");

                            b1.ToTable("Utilizador", "dbo");

                            b1.WithOwner()
                                .HasForeignKey("AuthUtilizador");
                        });

                    b.OwnsOne("DDDSample1.Domain.Authz.Email", "Email", b1 =>
                        {
                            b1.Property<string>("AuthUtilizador")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("Nome")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("AuthUtilizador");

                            b1.ToTable("Utilizador", "dbo");

                            b1.WithOwner()
                                .HasForeignKey("AuthUtilizador");
                        });

                    b.OwnsOne("DDDSample1.Domain.Authz.Funcao", "Funcao", b1 =>
                        {
                            b1.Property<string>("AuthUtilizador")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("Nome")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("AuthUtilizador");

                            b1.ToTable("Utilizador", "dbo");

                            b1.WithOwner()
                                .HasForeignKey("AuthUtilizador");
                        });

                    b.OwnsOne("DDDSample1.Domain.Authz.Nome", "Nome", b1 =>
                        {
                            b1.Property<string>("AuthUtilizador")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("N")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("AuthUtilizador");

                            b1.ToTable("Utilizador", "dbo");

                            b1.WithOwner()
                                .HasForeignKey("AuthUtilizador");
                        });

                    b.OwnsOne("DDDSample1.Domain.Authz.Telemovel", "Telemovel", b1 =>
                        {
                            b1.Property<string>("AuthUtilizador")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("Nome")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("AuthUtilizador");

                            b1.ToTable("Utilizador", "dbo");

                            b1.WithOwner()
                                .HasForeignKey("AuthUtilizador");
                        });

                    b.Navigation("Ativo");

                    b.Navigation("Email");

                    b.Navigation("Funcao");

                    b.Navigation("Nome");

                    b.Navigation("Telemovel");
                });

            modelBuilder.Entity("DDDSample1.Domain.Entregas.Entrega", b =>
                {
                    b.HasOne("DDDSample1.Domain.Armazens.Armazem", "armazem")
                        .WithMany("Entregas")
                        .HasForeignKey("armazemId");

                    b.OwnsOne("DDDSample1.Domain.Entregas.Tempo", "tempoColocar", b1 =>
                        {
                            b1.Property<string>("EntregaId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<double>("tempo")
                                .HasColumnType("float");

                            b1.HasKey("EntregaId");

                            b1.ToTable("Entregas", "dbo");

                            b1.WithOwner()
                                .HasForeignKey("EntregaId");
                        });

                    b.OwnsOne("DDDSample1.Domain.Entregas.Tempo", "tempoRetirar", b1 =>
                        {
                            b1.Property<string>("EntregaId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<double>("tempo")
                                .HasColumnType("float");

                            b1.HasKey("EntregaId");

                            b1.ToTable("Entregas", "dbo");

                            b1.WithOwner()
                                .HasForeignKey("EntregaId");
                        });

                    b.OwnsOne("DDDSample1.Domain.Entregas.Massa", "massa", b1 =>
                        {
                            b1.Property<string>("EntregaId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<double>("massa")
                                .HasColumnType("float");

                            b1.HasKey("EntregaId");

                            b1.ToTable("Entregas", "dbo");

                            b1.WithOwner()
                                .HasForeignKey("EntregaId");
                        });

                    b.Navigation("armazem");

                    b.Navigation("massa");

                    b.Navigation("tempoColocar");

                    b.Navigation("tempoRetirar");
                });

            modelBuilder.Entity("DDDSample1.Domain.EntregasInfo.EntregaInfo", b =>
                {
                    b.HasOne("DDDSample1.Domain.Armazens.Armazem", "armazemAdjacente")
                        .WithMany()
                        .HasForeignKey("armazemAdjacenteId");

                    b.HasOne("DDDSample1.Domain.Armazens.Armazem", "armazem")
                        .WithMany()
                        .HasForeignKey("armazemId");

                    b.OwnsOne("DDDSample1.Domain.EntregasInfo.Url", "urlArmazem", b1 =>
                        {
                            b1.Property<string>("EntregaInfoId")
                                .HasColumnType("nvarchar(450)");

                            b1.Property<string>("url")
                                .HasColumnType("nvarchar(max)");

                            b1.HasKey("EntregaInfoId");

                            b1.ToTable("EntregasInfo", "dbo");

                            b1.WithOwner()
                                .HasForeignKey("EntregaInfoId");
                        });

                    b.Navigation("armazem");

                    b.Navigation("armazemAdjacente");

                    b.Navigation("urlArmazem");
                });

            modelBuilder.Entity("DDDSample1.Domain.Armazens.Armazem", b =>
                {
                    b.Navigation("Entregas");
                });
#pragma warning restore 612, 618
        }
    }
}
