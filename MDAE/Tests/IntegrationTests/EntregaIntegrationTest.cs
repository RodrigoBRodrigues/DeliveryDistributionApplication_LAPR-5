using DDDSample1.Domain.Shared;
using DDDSample1.Controllers;
using System;
using NUnit.Framework;
using Moq;
using System.Threading.Tasks;
using System.Collections.Generic;
using DDDSample1.Domain.Armazens;
using DDDSample1.Domain.Entregas;

namespace Tests.Controller
{
    [TestFixture]
    public class IntroductionRequestControllerTest
    {
        private EntregasController _controller;

        private Mock<IEntregaService> _introEntregaServiceMock;


        private EntregaDto _creatingEntregaDto;

        private EntregaDto _invalidCreatingDto;


        /* Armazem */
        private ArmazemId idArmazem; private string armazemId;
        private Designacao designacao; private string nome;
        private Endereco endereco; private string rua; private string codigoPostal;
        private Localizacao localizacao; private double latitude; private double longitude; private double altitude;

        private EntregaId id; private string entregaId;
        private DateTime data; private string dataString;
        private Armazem armazem;
        private Massa massa; private double massaValor;
        private Tempo tempoColocar; private double tempo1;
        private Tempo tempoRetirar; private double tempo2;
        private Entrega entrega;


        [SetUp]
        public void Setup()
        {
            this._introEntregaServiceMock = new Mock<IEntregaService>();
            armazemId = "X77";
            idArmazem = new ArmazemId(armazemId);
            nome = "Anadia";
            designacao = new Designacao(nome);
            rua = "Santo Amaro"; codigoPostal = "3780-777";
            endereco = new Endereco(rua, codigoPostal);
            latitude = 2.0; longitude = 2.0; altitude = 200;
            localizacao = new Localizacao(latitude, longitude, altitude);

            entregaId = "1";
            id = new EntregaId(entregaId);
            dataString = "2022-11-03T00:00:00";
            data = DateTime.Parse(dataString);
            armazem = new Armazem(armazemId, nome, rua, codigoPostal, latitude, longitude, altitude,true);
            massaValor = 10000.0;
            massa = new Massa(massaValor);
            tempo1 = 10.0;
            tempo2 = 20.0;
            tempoColocar = new Tempo(tempo1);
            tempoRetirar = new Tempo(tempo2);
            entrega = new Entrega(entregaId, data, massaValor, armazem, tempo1, tempo2); ;

            this._creatingEntregaDto = new EntregaDto();
            _creatingEntregaDto.data = data;
            _creatingEntregaDto.massa = massaValor;
            _creatingEntregaDto.armazemDesignacao = armazemId;
            _creatingEntregaDto.tempoColocar = tempo1;
            _creatingEntregaDto.tempoRetirar = tempo2;

            this._invalidCreatingDto = new EntregaDto();
            _invalidCreatingDto.data = data;
            _invalidCreatingDto.massa = massaValor;
            _invalidCreatingDto.armazemDesignacao = armazemId;
            _invalidCreatingDto.tempoColocar = tempo1;
            _invalidCreatingDto.tempoRetirar = tempo2;

            this._controller = new EntregasController(this._introEntregaServiceMock.Object);

        }


        [Test]
        public void ShouldCreateEntregaStatus200()
        {
            //STUB
            var result = this._controller.Create(this._creatingEntregaDto);
            this._introEntregaServiceMock.Verify(t => t.AddAsync(It.IsAny<EntregaDto>()), Times.Once());
        }
    }
}