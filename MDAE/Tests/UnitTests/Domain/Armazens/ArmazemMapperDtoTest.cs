using System.Collections.Generic;
using DDDSample1.Domain.Armazens;
using System;
using NUnit.Framework;

namespace Tests.Domain.Armazens{
    public class ArmazemDtoTest{

        private ArmazemId id; private string armazemId; 
        private Designacao designacao; private string nome;
        private Endereco endereco; private  string rua; private string codigoPostal;
        private Localizacao localizacao; private double latitude; private double longitude; private double altitude;
        private Armazem armazem;

        [SetUp]
        public void SetUp() {

            armazemId = "X77";
            id = new ArmazemId(armazemId);
            nome = "Anadia";
            designacao = new Designacao(nome);
            rua = "Santo Amaro"; codigoPostal = "3780-777";
            endereco = new Endereco(rua,codigoPostal);
            latitude = 2.0; longitude = 2.0; altitude = 200;
            localizacao = new Localizacao(latitude, longitude, altitude);

            armazem = new Armazem (armazemId, nome, rua, codigoPostal, latitude, longitude, altitude, true);
        }

        [Test]
        public void VerificarInformacoesDoArmazemDTO() {

            ArmazemDto dto = ArmazemMapper.toArmazemDto(armazem);
        
            Assert.AreEqual(dto.Id,id.AsString());
            Assert.AreEqual(dto.Designacao,designacao.Nome);
            Assert.AreEqual(dto.Rua,endereco.Rua);
            Assert.AreEqual(dto.CodigoPostal,endereco.CodigoPostal);
            Assert.AreEqual(dto.Latitude,localizacao.Latitude);
            Assert.AreEqual(dto.Longitude,localizacao.Longitude);
            Assert.AreEqual(dto.Altitude,localizacao.Altitude);
        }        

        [Test]
        public void creatingToArmazemDomain() {
            ArmazemDto dto = ArmazemMapper.toArmazemDto(armazem);

            Armazem novo = ArmazemMapper.creatingToArmazemDomain(dto);

            Assert.AreEqual(novo.ToString(),armazem.ToString());
        }

    }

}