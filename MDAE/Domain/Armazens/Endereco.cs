using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;

namespace DDDSample1.Domain.Armazens{

    public class Endereco : IValueObject {

        public string Rua { get ; private set;}

        public string CodigoPostal {get; private set;}

        public Endereco (string rua, string codigoPostal)
        {
            validarRua(rua);
            this.Rua = rua;
            validarCodigoPostal(codigoPostal);
            this.CodigoPostal = codigoPostal;
        }

        public void validarRua(string rua){
            if (String.IsNullOrEmpty(rua)){
                throw new BusinessRuleValidationException("Rua inválida!");
            }
        }

         public void validarCodigoPostal(string codigoPostal){
             Regex codigoPt = new Regex("[0-9]{4}-[0-9]{3}");
            if (codigoPostal.Length !=8 || !codigoPt.IsMatch(codigoPostal.ToString())){
                throw new BusinessRuleValidationException("Código Postal Inválido!");
            }
        }
        
        public Endereco(){ }
       
    }

}