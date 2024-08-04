using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Armazens;
using System;

namespace DDDSample1.Domain.EntregasInfo
{
    public class EntregaInfo : Entity<EntregaInfoId>, IAggregateRoot
    {

        public virtual Armazem armazem { get; private set; }
        public virtual Armazem armazemAdjacente { get; set; }
        public virtual Url urlArmazem { get; set; }


       
        public EntregaInfo()
        {
           
        }

        public void AssociateId(string code)
        {
            this.Id = new EntregaInfoId(code);
        }        
        public void AssociateUrl(string urlArmazem)
        {
            this.urlArmazem = new Url(urlArmazem);
        }        

        public void AssociateArmazem(Armazem armazem)
        {
            this.armazem = armazem;
        }

        public void AssociateArmazensAdjacentes(Armazem armazens)
        {
            this.armazemAdjacente = armazens;
        }

    }
}