using System.Collections.Generic;
using DDDSample1.Domain.Armazens;
using DDDSample1.Domain.Shared;
using System;
using DDDSample1.Controllers;
using DDDSample1.Domain.Entregas;
using Microsoft.Extensions.Logging;
using NUnit.Framework;
using Moq;



namespace Tests.Domain.Armazens
{
    public class EntregaServiceTest
    {

        private ArmazemId id; private string armazemId;
        private Designacao designacao; private string nome;
        private Endereco endereco; private string rua; private string codigoPostal;
        private Localizacao localizacao; private double latitude; private double longitude; private double altitude;
        private Armazem armazem;
        private Entrega entrega;
        private readonly Mock<IUnitOfWork> unitOfWorkMock = new Mock<IUnitOfWork>();
        private readonly Mock<IArmazemRepository> armazemRepositoryMock = new Mock<IArmazemRepository>();
        private readonly Mock<IEntregaRepository> entregaRepositoryMock = new Mock<IEntregaRepository>();
        private ArmazemService service;
        private EntregaService serviceEntrega;



        private EntregaDto dto;



        [SetUp]
        public void SetUp()
        {
            armazemId = "X77";
            id = new ArmazemId(armazemId);
            nome = "Anadia";
            designacao = new Designacao(nome);
            rua = "Santo Amaro"; codigoPostal = "3780-777";
            endereco = new Endereco(rua, codigoPostal);
            latitude = 2.0; longitude = 2.0; altitude = 200;
            localizacao = new Localizacao(latitude, longitude, altitude);

            string testData = "2022-12-22T00:00:00";
            string testArmazemId = "XX2";
            double testMassa = 8000.0;
            double testTempoColocar = 10.0;
            double testTempoRetirar = 20.0;


            dto = new EntregaDto();
            dto.Id = "2";
            dto.data = DateTime.Parse(testData);
            dto.armazemDesignacao = "testArmazemId";
            dto.massa = testMassa;
            dto.tempoColocar = testTempoColocar;
            dto.tempoRetirar = testTempoRetirar;




            armazem = new Armazem(armazemId, nome, rua, codigoPostal, latitude, longitude, altitude,true);
            entrega = new Entrega(testArmazemId, DateTime.Parse(testData), testMassa, testTempoColocar, testTempoRetirar);

            service = new ArmazemService(unitOfWorkMock.Object, armazemRepositoryMock.Object);
            serviceEntrega = new EntregaService(unitOfWorkMock.Object, entregaRepositoryMock.Object,
                armazemRepositoryMock.Object);

            this.armazemRepositoryMock.Setup(t => t.AddAsync(It.IsAny<Armazem>()));
            this.armazemRepositoryMock.Setup(t => t.Add(It.IsAny<Armazem>()));
            this.armazemRepositoryMock.Setup(t => t.GetByIdAsync(It.IsAny<ArmazemId>()));
            this.armazemRepositoryMock.Setup(t => t.GetByDesignacao(It.IsAny<Designacao>()));
            this.armazemRepositoryMock.Setup(t => t.GetAllAsync());


            this.entregaRepositoryMock.Setup(t => t.AddAsync(It.IsAny<Entrega>()));
            this.entregaRepositoryMock.Setup(t => t.GetByIdAsync(It.IsAny<EntregaId>()));
        }


        [Test]
        public void CreateEntrega()
        {

            var _mockLogger = new Mock<ILogger<ValuesController>>();

            EntregaDto dtoEntrega = EntregaMapper.toEntregaDto(entrega);

            var result1 = this.serviceEntrega.AddAsync(dtoEntrega);
            this.entregaRepositoryMock.Verify(t => t.AddAsync(It.IsAny<Entrega>()), Times.Never);

            var result2 = this.serviceEntrega.GetByIdAsync(new EntregaId(dto.Id));
            this.entregaRepositoryMock.Verify(t => t.GetByIdAsync(It.IsAny<EntregaId>()), Times.AtLeastOnce());

            Assert.AreEqual(0, _mockLogger.Invocations.Count);
            Assert.AreEqual(result1.ToString(), result2.ToString());
        }

        [Test]
        public void UpdateArmazem()
        {
            var _mockLogger = new Mock<ILogger<ValuesController>>();

            EntregaDto dtoEntrega = EntregaMapper.toEntregaDto(entrega);

            var result1 = this.serviceEntrega.AddAsync(dtoEntrega);
            this.entregaRepositoryMock.Verify(t => t.AddAsync(It.IsAny<Entrega>()), Times.Never);

            var result2 = this.serviceEntrega.UpdateAsync(dto);
            this.entregaRepositoryMock.Verify(t => t.GetByIdAsync(It.IsAny<EntregaId>()), Times.AtLeastOnce());

            var result3 = this.serviceEntrega.GetByIdAsync(new EntregaId(dto.Id));
            this.entregaRepositoryMock.Verify(t => t.GetByIdAsync(It.IsAny<EntregaId>()), Times.AtLeastOnce());

            Assert.AreEqual(0, _mockLogger.Invocations.Count);
            Assert.AreNotEqual(result1, result2);
            Assert.AreEqual(result2.ToString(), result3.ToString());


        }

        [Test]
        public void GetArmazens()
        {
            EntregaDto dtoEntrega = EntregaMapper.toEntregaDto(entrega);

            var result1 = this.serviceEntrega.AddAsync(dto);
            this.entregaRepositoryMock.Verify(t => t.AddAsync(It.IsAny<Entrega>()), Times.Never());

            var result2 = this.serviceEntrega.GetAllAsync();
            this.entregaRepositoryMock.Verify(t => t.GetAllAsync(), Times.AtLeastOnce());

            Assert.AreNotEqual(result1, result2);
            var _mockLogger = new Mock<ILogger<ValuesController>>();


            Assert.AreEqual(0, _mockLogger.Invocations.Count);
            Assert.AreNotEqual(result1, result2);
        }


    }

}