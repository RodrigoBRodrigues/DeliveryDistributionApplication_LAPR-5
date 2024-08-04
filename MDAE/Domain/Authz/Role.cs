using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;

namespace DDDSample1.Domain.Authz{

    public class Role : IValueObject {

        public string Nome { get ; private set;}

        public Role (string nome)
        {
            this.Nome = nome;
        }
    }
}