using DDDSample1.Domain.Armazens;
using System;
using System.Threading.Tasks;
using DDDSample1.Domain.Entregas;
using NUnit.Framework;

namespace Tests.Domain.Entregas{
    public class EntregaTest{

        /* Armazem */
        private ArmazemId idArmazem; private string armazemId; 
        private Designacao designacao; private string nome;
        private Endereco endereco; private  string rua; private string codigoPostal;
        private Localizacao localizacao; private double latitude; private double longitude; private double altitude;
        private string dataExpected;

        private EntregaId id; private string entregaId; 
        private DateTime data; private string dataString;
        private Armazem armazem;
        private Massa massa; private double massaValor;
        private Tempo tempoColocar; private double tempo1;
        private Tempo tempoRetirar; private double tempo2;
        private DateTime datetimeExcepted;
        private DateTime datetimeExcepted2;
        private Entrega entrega;
        [SetUp]
        public void SetUp() {
            armazemId = "X77";
            idArmazem = new ArmazemId(armazemId);
            nome = "Anadia";
            designacao = new Designacao(nome);
            rua = "Santo Amaro"; codigoPostal = "3780-777";
            endereco = new Endereco(rua,codigoPostal);
            latitude = 2.0; longitude = 2.0; altitude = 200;
            localizacao = new Localizacao(latitude, longitude, altitude);

            entregaId = "1";
            id = new EntregaId(entregaId);
            dataString = "2022-11-03T00:00:00";
            dataExpected = "2022-11-03T00:00:00";
            data = DateTime.Parse(dataString);
            datetimeExcepted = DateTime.Parse(dataExpected);
            armazem = new Armazem (armazemId, nome, rua, codigoPostal, latitude, longitude, altitude, true);
            massaValor = 10000.0;
            massa = new Massa(massaValor);
            tempo1 = 10.0;
            tempo2 = 20.0;
            tempoColocar = new Tempo(tempo1);
            tempoRetirar = new Tempo(tempo2);
            entrega = new Entrega(entregaId, data, massaValor, armazem, tempo1, tempo2);
        }

        [Test]
        public void VerificarInformacoesDaEntrega() {
            Assert.AreEqual(entrega.Id,id);
            Assert.AreEqual(entrega.data, datetimeExcepted);
            Assert.AreEqual(entrega.massa.massa,massaValor);
            Assert.AreEqual(entrega.armazem.Id,idArmazem);
            Assert.AreEqual(entrega.tempoColocar.tempo,tempo1);
            Assert.AreEqual(entrega.tempoRetirar.tempo,tempo2);
        }        

        [Test]
        public void AlterarInformacoesDoArmazem() {
            string dataNova = "2022-12-01T00:00:00";
            string dataExcepted = "2022-12-01T00:00:00";
            datetimeExcepted2 = DateTime.Parse(dataExcepted); 
            entrega.ChangeData(DateTime.Parse(dataNova));
            Assert.AreEqual(entrega.data, datetimeExcepted2);
            
            double massa = 15000.0;
            entrega.ChangeMassa(massa);
            Assert.AreEqual(entrega.massa.massa, massa);

            Armazem armazem = new Armazem("XX1", "Vale de Cambra", "Rua do Caima", "3730-210"
                , 10.0, 30.0, 300, true);
            entrega.AssociateArmazem(armazem);
            Assert.AreEqual(entrega.armazem, armazem);
            
            double tempoColocar = 20.0;
            entrega.ChangeTempoColocar(tempoColocar);
            Assert.AreEqual(entrega.tempoColocar.tempo, tempoColocar);
            
            double tempoRetirar = 50.0;
            entrega.ChangeTempoRetirar(tempoRetirar);
            Assert.AreEqual(entrega.tempoRetirar.tempo, tempoRetirar);
        }

    }

}