using DDDSample1.Domain.Shared;

namespace DDDSample1.Domain.Entregas
{
    // Isto representa a massa em quilogramas
    public class Massa : IValueObject
    {

        public double massa { get; private set; }


        public Massa(double massa)
        {
            validarMassa(massa);
            this.massa = massa;
        }

        public void validarMassa(double massa)
        {
            if (massa < 0.0)
            {
                throw new BusinessRuleValidationException("Massa inválida! Por favor tente outra");
            }

            /**
                TODO: Fazer um else para caso a massa seja maior que o peso util do camiao
            **/
        }

        
    }

}