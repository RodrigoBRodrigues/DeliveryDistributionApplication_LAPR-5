using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;

namespace DDDSample1.Domain.Authz{

    public class Nome : IValueObject {

        public string N { get ; private set;}

        public Nome (string N)
        {
            this.N = N;
        }
    }
}