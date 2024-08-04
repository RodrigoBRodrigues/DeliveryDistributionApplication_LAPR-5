using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;

namespace DDDSample1.Domain.Armazens{

    public class Localizacao : IValueObject {

        public double Latitude {get; private set;}
        public double Longitude { get ; private set;}
        
        public double Altitude {get; private set;}

        public Localizacao (double latitude,double longitude, double altitude)
        {
            validarLocalizacao(longitude,latitude,altitude);
            this.Longitude = longitude;
            this.Latitude = latitude;
            this.Altitude = altitude;
        }

        public void validarLocalizacao (double lon, double lat, double alt) {
                if ( lon < 0 || lon > 180 || lat < 0 || lat > 90 || alt < 0) {
                throw new BusinessRuleValidationException("Localização inválida!");
                }
        }

        public Localizacao(){ }
       
    }
}
