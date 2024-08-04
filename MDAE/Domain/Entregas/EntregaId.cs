using System;
using DDDSample1.Domain.Shared;

using Newtonsoft.Json;
namespace DDDSample1.Domain.Entregas
{
    public class EntregaId : EntityId
    {
        [JsonConstructor]
        public EntregaId (Guid value) : base(value)
        {
        }

        public EntregaId(String value):base(value)
        {

        }

        override
        protected  Object createFromString(String text){
            return text;
        }
        override
        public String AsString(){
            return (String) base.Value;
        }

        public Guid AsGuid(){
            return (Guid) base.ObjValue;
        }
    }
}