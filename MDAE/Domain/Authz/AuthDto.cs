using System;

namespace DDDSample1.Domain.Authz
{
    public class AuthDto
    {
        public String Utilizador { get; set; }
        public String Nome { get; set; }
        public String Role { get; set; }
        public String Telemovel { get; set; }
        public String Email { get; set; }
        public bool Ativo { get; set; }

    }
}