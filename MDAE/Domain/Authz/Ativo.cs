using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;

namespace DDDSample1.Domain.Authz{

    public class Ativo : IValueObject {

        public bool ativo { get ; private set;}

        public Ativo (bool ativo)
        {
            this.ativo = ativo;
        }

    }
}