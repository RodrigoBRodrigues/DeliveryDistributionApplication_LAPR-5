using System.Collections.Generic;
using DDDSample1.Domain.Armazens;
using DDDSample1.Domain.Entregas;
using DDDSample1.Domain.Shared;
using DDDSample1.Controllers;
using System;
using NUnit.Framework;
using Moq;


namespace Tests.Controller
{
    [TestFixture]
    public class ArmazemIntegrationTest
    {

        private ArmazemId id; private string armazemId;
        private Designacao designacao; private string nome;
        private Endereco endereco; private string rua; private string codigoPostal;
        private Localizacao localizacao; private double latitude; private double longitude; private double altitude;
        private Armazem armazem;
        private ArmazensController armazemController;
        private Mock<ArmazemService> _introArmazemServiceMock;
        private readonly Mock<IUnitOfWork> unitOfWorkMock = new Mock<IUnitOfWork>();
        private readonly Mock<IArmazemRepository> repositoryMock = new Mock<IArmazemRepository>();

        private EntregaId Eid; private string entregaId;
        private DateTime data; private string dataString;
        private Massa massa; private double massaValor;
        private Tempo tempoColocar; private double tempo1;
        private Tempo tempoRetirar; private double tempo2;
        private Entrega entrega;
        private EntregasController entregaController;
        private Mock<IEntregaService> _introEntregaServiceMock;
        private EntregaDto _creatingEntregaDto;

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
            armazem = new Armazem(armazemId, nome, rua, codigoPostal, latitude, longitude, altitude,true);

            Assert.AreEqual(armazem.Id, id);
            Assert.AreEqual(armazem.Designacao.Nome, designacao.Nome);
            Assert.AreEqual(armazem.Endereco.Rua, endereco.Rua);
            Assert.AreEqual(armazem.Endereco.CodigoPostal, endereco.CodigoPostal);
            Assert.AreEqual(armazem.Localizacao.Latitude, localizacao.Latitude);
            Assert.AreEqual(armazem.Localizacao.Longitude, localizacao.Longitude);
            Assert.AreEqual(armazem.Localizacao.Altitude, localizacao.Altitude);

            entregaId = "1";
            Eid = new EntregaId(entregaId);
            dataString = "2022-11-03T00:00:00";
            data = DateTime.Parse(dataString);
            armazem = new Armazem(armazemId, nome, rua, codigoPostal, latitude, longitude, altitude,true);
            massaValor = 10000.0;
            massa = new Massa(massaValor);
            tempo1 = 10.0;
            tempo2 = 20.0;
            tempoColocar = new Tempo(tempo1);
            tempoRetirar = new Tempo(tempo2);
            entrega = new Entrega(entregaId, data, massaValor, armazem, tempo1, tempo2);

            this._creatingEntregaDto = new EntregaDto();
            _creatingEntregaDto.data = data;
            _creatingEntregaDto.massa = massaValor;
            _creatingEntregaDto.armazemDesignacao = armazemId;
            _creatingEntregaDto.tempoColocar = tempo1;
            _creatingEntregaDto.tempoRetirar = tempo2;

            this._introEntregaServiceMock = new Mock<IEntregaService>();
            this._introArmazemServiceMock = new Mock<ArmazemService>(unitOfWorkMock.Object, repositoryMock.Object);

            this.armazemController = new ArmazensController(this._introArmazemServiceMock.Object);
            this.entregaController = new EntregasController(this._introEntregaServiceMock.Object);
        }


        [Test]
        public void CreateArmazem_E_AssociarAEntrega()
        {

            ArmazemDto dto1 = ArmazemMapper.toArmazemDto(armazem);

            var result1 = this.armazemController.Create(dto1);
            this.repositoryMock.Verify(t => t.AddAsync(It.IsAny<Armazem>()), Times.Once());

            var result2 = this.entregaController.Create(this._creatingEntregaDto);
            this._introEntregaServiceMock.Verify(t => t.AddAsync(It.IsAny<EntregaDto>()), Times.Once());

            Assert.AreEqual(dto1.Id, this._creatingEntregaDto.armazemDesignacao);
        }

        [Test]
        public void VerificarArmazem_AssociadoAEntrega()
        {

            ArmazemDto dto1 = ArmazemMapper.toArmazemDto(armazem);

            var result1 = this.armazemController.Create(dto1);
            this.repositoryMock.Verify(t => t.AddAsync(It.IsAny<Armazem>()), Times.AtLeastOnce());

            var result2 = this.entregaController.Create(this._creatingEntregaDto);
            this._introEntregaServiceMock.Verify(t => t.AddAsync(It.IsAny<EntregaDto>()), Times.Once());

            var result3 = this.entregaController.GetByArmazem("X77");
            this._introEntregaServiceMock.Verify(t => t.GetEntregasByArmazem(It.IsAny<string>()), Times.AtLeastOnce());

            Assert.AreEqual(this._creatingEntregaDto.armazemDesignacao, dto1.Id);
        }
    }

}