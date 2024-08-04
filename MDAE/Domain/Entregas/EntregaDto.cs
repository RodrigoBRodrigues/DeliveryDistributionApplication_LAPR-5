using System;


namespace DDDSample1.Domain.Entregas
{
    public class EntregaDto
    {
        public String Id { get; set; }
        public DateTime data { get; set; }
        public double massa { get; set; }
        public String armazemDesignacao { get; set; }
        public double tempoColocar { get; set; }
        public double tempoRetirar { get; set; }

    }
}