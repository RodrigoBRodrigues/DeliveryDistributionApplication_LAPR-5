using System;
using DDDSample1.Domain.Armazens;

namespace DDDSample1.Domain.EntregasInfo
{
    public class EntregaInfoDto
    {
        public String Id { get; set; }
        public String armazem { get; set; }
        public String armazemAdjacente { get; set; }

        public string urlArmazem { get; set; }
    }
}