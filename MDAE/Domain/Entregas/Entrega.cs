using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Armazens;
using System;

namespace DDDSample1.Domain.Entregas
{
    public class Entrega : Entity<EntregaId>, IAggregateRoot
    {

        public DateTime data { get; private set; }
        public virtual Armazem armazem { get; private set; }
        public Massa massa { get; private set; }
        public Tempo tempoColocar { get; private set; }
        public Tempo tempoRetirar { get; private set; }



        public Entrega()
        {
        }

        public Entrega(string code, DateTime data, double massa
        , double tempoColocar, double tempoRetirar)
        {
            this.Id = new EntregaId(code);
            this.data = data;
            this.massa = new Massa(massa);
            this.tempoColocar = new Tempo(tempoColocar);
            this.tempoRetirar = new Tempo(tempoRetirar);
        }

        public Entrega(string code, DateTime data, double massa
            , Armazem armazem, double tempoColocar, double tempoRetirar)
        {
            this.Id = new EntregaId(code);
            this.data = data;
            this.massa = new Massa(massa);
            this.armazem = armazem;

            this.tempoColocar = new Tempo(tempoColocar);
            this.tempoRetirar = new Tempo(tempoRetirar);
        }


        public Entrega(string code)
        {
            this.Id = new EntregaId(code);
        }

        public void ChangeData(DateTime data)
        {
            this.data = data;
        }

        public void ChangeMassa(double massa)
        {
            this.massa = new Massa(massa);
        }
        public void ChangeTempoColocar(double tempoColocar)
        {
            this.tempoColocar = new Tempo(tempoColocar);
        }
        public void ChangeTempoRetirar(double tempoRetirar)
        {
            this.tempoRetirar = new Tempo(tempoRetirar);
        }

        public void AssociateArmazem(Armazem armazem)
        {
            this.armazem = armazem;
        }

    }
}