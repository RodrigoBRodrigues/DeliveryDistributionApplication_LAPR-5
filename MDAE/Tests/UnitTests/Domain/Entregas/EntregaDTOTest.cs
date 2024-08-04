using DDDSample1.Domain.Entregas;
using NUnit.Framework;
using System;
using System.Threading.Tasks;

namespace Tests
{

    public class EntregaDTOTest
    {
        [Test]
        public void testSetParameters()
        {
            string testEntregaId = "test1";
            string testData = "2022-12-22T00:00:00";
            string testArmazemId = "XX2";
            double testMassa = 8000.0;
            double testTempoRetirar = 20.0;
            double testTempoColocar = 10.0;

            EntregaDto dto = new EntregaDto();
            dto.Id = testEntregaId;
            dto.data = DateTime.Parse(testData);
            dto.armazemDesignacao = testArmazemId;
            dto.massa = testMassa;
            dto.tempoColocar = testTempoColocar;
            dto.tempoRetirar = testTempoRetirar;

            Assert.AreEqual(dto.Id, "test1");
            Assert.AreEqual(dto.data, DateTime.Parse(testData));
            Assert.AreEqual(dto.massa, 8000.0); //testar validacao
            Assert.AreEqual(dto.armazemDesignacao, "XX2");
            Assert.AreEqual(dto.tempoColocar, 10.0);
            Assert.AreEqual(dto.tempoRetirar, 20.0);


        }

    }

}