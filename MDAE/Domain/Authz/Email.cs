using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;

namespace DDDSample1.Domain.Authz{

    public class Email : IValueObject {

        public string Nome { get ; private set;}

        public Email (string nome)
        {
            this.Nome = nome;
        }


    }
}