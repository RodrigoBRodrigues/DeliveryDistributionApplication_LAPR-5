using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System;
using System.Threading.Tasks;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Armazens;

namespace DDDSample1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ArmazensController : ControllerBase
    {
        private readonly ArmazemService _service;

        public ArmazensController(ArmazemService service)
        {
            _service = service;
        }

        // GET: api/Armazens
        [HttpGet]
        public async Task<ActionResult<IEnumerable<ArmazemDto>>> GetAll()
        {
            return await _service.GetAllAsync();
        }

        // GET: api/Armazens/5
        [HttpGet("{id}")]
        public async Task<ActionResult<ArmazemDto>> GetGetById(String id)
        {
            var cat = await _service.GetByIdAsync(new ArmazemId(id));

            if (cat == null)
            {
                return NotFound();
            }

            return cat;
        }

        // POST: api/Armazens
        [HttpPost]
        public async Task<ActionResult<ArmazemDto>> Create(ArmazemDto dto)
        {
            var arm = await _service.AddAsync(dto);

            return CreatedAtAction(nameof(GetGetById), new { 
                                   Id = arm.Id,
                                   Designacao = arm.Designacao,
                                   Rua = arm.Rua,
                                   CodigoPostal = arm.CodigoPostal,
                                   Latitude = arm.Latitude,
                                   Longitude = arm.Longitude,
                                   Altitude = arm.Altitude}, arm);
        }

        
         // PUT: api/Armazem/E5
        [HttpPut("{id}")]
        public async Task<ActionResult<ArmazemDto>> Update(String id, ArmazemDto dto)
        {
            if (id != dto.Id)
            {
                return BadRequest();
            }

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

        [HttpPatch("{id}")]
        public async Task<ActionResult<ArmazemDto>> Patch(String id, Ativo ativo)
        {
            var dto = await _service.GetByIdAsync(new ArmazemId(id));

            if (dto == null)
            {
                return NotFound();
            }

            try
            {
                var arm = await _service.PatchAtivo(dto, ativo);

                if (arm == null)
                {
                    return NotFound();
                }

                return Ok(arm);
            }

            catch(BusinessRuleValidationException ex)
            {
               return BadRequest(new {Message = ex.Message});
            }
        }
       
        // DELETE: api/Categories/5
        [HttpDelete("{id}")]
        public async Task<ActionResult<ArmazemDto>> HardDelete(String id)
        {
            try
            {
                var arm = await _service.DeleteAsync(new ArmazemId(id));

                if (arm == null)
                {
                    return NotFound();
                }

                return Ok(arm);
            }
            catch(BusinessRuleValidationException ex)
            {
               return BadRequest(new {Message = ex.Message});
            }
        }

        // GET: api/Armazens/5
        [HttpGet("designacao/{designacao}")]
        public async Task<ActionResult<ArmazemDto>> GetByDesignacao(string designacao)
        {
            var cat = await _service.GetByDesignacao(designacao);

            if (cat == null)
            {
                return NotFound();
            }

            return cat;
        }

        // GET: api/Armazens/ativos
        [HttpGet("ativos={ativo}")]
        public async  Task<ActionResult<IEnumerable<ArmazemDto>>> GetAtivos(bool ativo)
        {
             return await _service.GetAtivos(ativo);
        }
    }
}