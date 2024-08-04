using DDDSample1.Domain.Shared;

namespace DDDSample1.Domain.Entregas
{

    // Isto representa o tempo em minutos
    // É possível representar 2.5 minutos
    public class Tempo : IValueObject
    {

        public double tempo { get; private set; }

        public Tempo(double tempo)
        {
            validarTempo(tempo);
            this.tempo = tempo;
        }

        public void validarTempo(double tempo)
        {
            if (tempo < 0)
            {
                throw new BusinessRuleValidationException("Tempo inválido!");
            }
        }

        public Tempo() { }

    }
}
