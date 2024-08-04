using DDDSample1.Domain.Entregas;
using NUnit.Framework;
using System;
using System.Threading.Tasks;

namespace Tests.Domain.Entregas
{

    public class EntregaDTOTest
    {
        [Test]
        public void testEntregaToDto()
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

            Entrega entrega = new Entrega(dto.Id, dto.data, dto.massa, dto.tempoColocar, dto.tempoRetirar);
            EntregaDto entregaMapper = EntregaMapper.toEntregaDto(entrega);

            Assert.AreEqual(dto.Id, entregaMapper.Id);
        }

        [Test]
        public void testEntregaToDomain()
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

            Entrega entrega = new Entrega(dto.Id, dto.data, dto.massa, dto.tempoColocar, dto.tempoRetirar);
            Entrega entregaMapper = EntregaMapper.CreatingToEntregaDomain(dto);

            Assert.AreEqual(entrega.massa.massa, entregaMapper.massa.massa);
        }
    }

}