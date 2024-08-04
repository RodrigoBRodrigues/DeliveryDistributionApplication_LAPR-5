using System.Collections.Generic;
using DDDSample1.Domain.Armazens;
using System;
using NUnit.Framework;

namespace Tests.Domain.Armazens{
    public class ArmazemTest{

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
        public void VerificarInformacoesDoArmazem() {
            Assert.AreEqual(armazem.Id,id);
            Assert.AreEqual(armazem.Designacao.Nome,designacao.Nome);
            Assert.AreEqual(armazem.Endereco.Rua,endereco.Rua);
            Assert.AreEqual(armazem.Endereco.CodigoPostal,endereco.CodigoPostal);
            Assert.AreEqual(armazem.Localizacao.Latitude,localizacao.Latitude);
            Assert.AreEqual(armazem.Localizacao.Longitude,localizacao.Longitude);
            Assert.AreEqual(armazem.Localizacao.Altitude,localizacao.Altitude);
        }        

        [Test]
        public void AlterarInformacoesDoArmazem() {
            string novaDescricao = "Murtosa";
            armazem.ChangeDesignacao(novaDescricao);
            Assert.AreEqual(armazem.Designacao.Nome, novaDescricao);

            string novaRua ="Santa Joana";
            string novoCodigoPostal = "7777-252";
            armazem.ChangeEndereco(novaRua,novoCodigoPostal);
            Assert.AreEqual(armazem.Endereco.Rua,novaRua);
            Assert.AreEqual(armazem.Endereco.CodigoPostal,novoCodigoPostal);

            double novaLatitude = 5.0;
            double novaLongitude = 5.0;
            double novaAltitude = 500;
            armazem.ChangeLocalizacao(novaLatitude,novaLongitude,novaAltitude);
            Assert.AreEqual(armazem.Localizacao.Latitude,novaLatitude);
            Assert.AreEqual(armazem.Localizacao.Longitude,novaLongitude);
            Assert.AreEqual(armazem.Localizacao.Altitude,novaAltitude);
        }

    }

}