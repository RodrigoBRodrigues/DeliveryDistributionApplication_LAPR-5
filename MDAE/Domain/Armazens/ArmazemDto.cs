using System;

namespace DDDSample1.Domain.Armazens
{
    public class ArmazemDto
    {
        public String Id { get; set; }
        public String Designacao {get; set;}
        public String Rua {get; set;}
        public String CodigoPostal {get; set;}
        public double Latitude {get; set;}
        public double Longitude {get; set;}
        public double Altitude {get; set;}
        public bool Ativo {get; set;}

    }
}