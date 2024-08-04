using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;

namespace DDDSample1.Domain.Authz{

    public class Telemovel : IValueObject {

        public string Nome { get ; private set;}

        public Telemovel (string nome)
        {
            this.Nome = nome;
        }
    }
}