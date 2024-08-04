using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System;
using System.Threading.Tasks;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Entregas;

namespace DDDSample1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class EntregasController : ControllerBase
    {
        private readonly IEntregaService _service;

        public EntregasController(IEntregaService service)
        {
            _service = service;
        }

        // GET: api/Entregas
        [HttpGet]
        public async Task<ActionResult<IEnumerable<EntregaDto>>> GetAll()
        {
            return await _service.GetAllAsync();
        }

        // GET: api/Entregas/E1
        [HttpGet("{id}")]
        public async Task<ActionResult<EntregaDto>> GetGetById(String id)
        {
            var ent = await _service.GetByIdAsync(new EntregaId(id));

            if (ent == null)
            {
                return NotFound();
            }

            return ent;
        }


        // GET: api/Entregas/{name}
        [HttpGet("dates")]
        public async Task<ActionResult<List<EntregaDto>>> GetBetweenDates([FromQuery(Name = "startdate")] string startDate,
         [FromQuery(Name = "finishdate")] string finishDate)
        {
            var ent = await _service.GetBetweenDates(startDate, finishDate);

            if (ent == null)
            {
                return NotFound();
            }

            return ent;
        }
        // GET: api/Entregas/{name}
        [HttpGet("date")]
        public async Task<ActionResult<List<EntregaDto>>> GetByDate([FromQuery(Name = "date")] string startDate)
        
        {
            var ent = await _service.GetByDate(startDate);

            if (ent == null)
            {
                return NotFound();
            }

            return ent;
        }
        
        // GET: api/Entregas/{name}
        [HttpGet("armazem")]
        public async Task<ActionResult<List<EntregaDto>>> GetByArmazem([FromQuery(Name = "armazem")] string armazem)
        {
            var ent = await _service.GetEntregasByArmazem(armazem);

            if (ent == null)
            {
                return NotFound();
            }
 
            return ent;
        }       




        // POST: api/entregas
        [HttpPost]
        public async Task<ActionResult<EntregaDto>> Create(EntregaDto dto)
        {
            var entrega = await _service.AddAsync(dto);

            return CreatedAtAction(nameof(GetGetById), new
            {
                Id = entrega.Id,
            }, entrega);
        }


        // PUT: api/Entregas/E5
        [HttpPut("{id}")]
        public async Task<ActionResult<EntregaDto>> Update(String id, EntregaDto dto)
        {
            dto.Id = id;
            
            try
            {
                var entrega = await _service.UpdateAsync(dto);

                if (entrega == null)
                {
                    return NotFound();
                }
                return Ok(entrega);
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }

        // DELETE: api/Entregas/E5
        [HttpDelete("{id}")]
        public async Task<ActionResult<EntregaDto>> HardDelete(String id)
        {
            try
            {
                var ent = await _service.DeleteAsync(new EntregaId(id));

                if (ent == null)
                {
                    return NotFound();
                }

                return Ok(ent);
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }
    }
}