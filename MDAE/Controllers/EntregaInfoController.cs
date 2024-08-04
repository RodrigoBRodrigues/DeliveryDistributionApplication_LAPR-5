using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System;
using System.Threading.Tasks;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Entregas;
using DDDSample1.Domain.EntregasInfo;

namespace DDDSample1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class EntregasInfoController : ControllerBase
    {
        private readonly IEntregaInfoService _service;

        public EntregasInfoController(IEntregaInfoService service)
        {
            _service = service;
        }

        // GET: api/Entregas
        [HttpGet]
        public async Task<ActionResult<IEnumerable<EntregaInfoDto>>> GetAll()
        {
            return await _service.GetAllAsync();
        }

        // GET: api/Entregas/E1
        [HttpGet("{id}")]
        public async Task<ActionResult<EntregaInfoDto>> GetGetById(String id)
        {
            var ent = await _service.GetByIdAsync(new EntregaInfoId(id));

            if (ent == null)
            {
                return NotFound();
            }

            return ent;
        }

        
        // GET: api/Entregas/{name}
        [HttpGet("armazem")]
        public async Task<ActionResult<List<EntregaInfoDto>>> GetByArmazem([FromQuery(Name = "armazem")] string armazem)
        {
            var ent = await _service.GetEntregasByArmazem(armazem);

            if (ent == null)
            {
                return NotFound();
            }
 
            return ent;
        }       




        // POST: api/entregasinfo
        [HttpPost]
        public async Task<ActionResult<EntregaInfoDto>> Create(EntregaInfoDto dto)
        {
            var entrega = await _service.AddAsync(dto);

            return CreatedAtAction(nameof(GetGetById), new
            {
                Id = entrega.Id,
            }, entrega);
        }


    
    }
}