using System;
using DDDSample1.Domain.Shared;

using Newtonsoft.Json;
namespace DDDSample1.Domain.EntregasInfo
{
    public class EntregaInfoId : EntityId
    {
        [JsonConstructor]
        public EntregaInfoId (Guid value) : base(value)
        {
        }

        public EntregaInfoId(String value):base(value)
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