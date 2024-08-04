using Microsoft.AspNetCore.Mvc;
using DDDSample1.Domain.Authz;
using Google.Apis.Auth;
using Microsoft.Extensions.Options;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Linq;
using System;

namespace DDDSample1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly AuthService _service;
        private readonly AppSettings _applicationSettings;

        public AuthController(AuthService service, IOptions<AppSettings> _applicationSettings)
        {
            _service = service;
            this._applicationSettings = _applicationSettings.Value;
            this._applicationSettings.GoogleClientId = "732388894245-44cp5l7gab8hi7ugkhgeo8kt4gmhdush.apps.googleusercontent.com";

        }

        // GET: api/Armazens
        [HttpGet]
        public async Task<ActionResult<IEnumerable<AuthDto>>> GetAll()
        {
            return await _service.GetAllAsync();
        }


        [HttpPost]
        public async Task<ActionResult<AuthDto>> Create(AuthDto dto)
        {
            var arm = await _service.AddAsync(dto);

            return arm;
        }

        [HttpPut("{utilizador}")]
        public async Task<ActionResult<AuthDto>> Cancel(String utilizador) {
            AuthDto AuthDTO = null;
            var utilizadoresData = await _service.GetAllAsync();
            for( int i = 0; i < utilizadoresData.Count; i++){
                if(utilizadoresData[i].Utilizador.Equals(utilizador)){
                    AuthDTO = utilizadoresData[i];
                    break;
                }
            }
            var user = await _service.Cancel(AuthDTO);
            return Ok(user);
        }

        public dynamic JWTGenerator(Auth user)
        {
            var tokenHandler = new JwtSecurityTokenHandler();
            this._applicationSettings.Secret = "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";
            var key = Encoding.ASCII.GetBytes(this._applicationSettings.Secret);

            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = new ClaimsIdentity(new[] { new Claim(ClaimTypes.Role, user.Role.Nome) }),
                Expires = DateTime.UtcNow.AddDays(7),
                SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha512Signature)
            };
            var token = tokenHandler.CreateToken(tokenDescriptor);
            var encrypterToken = tokenHandler.WriteToken(token);

            HttpContext.Response.Cookies.Append("token", encrypterToken);

            return new { token = encrypterToken, user = user.Email.Nome };
        }

        [HttpPost("LoginWithGoogle")]
        public async Task<IActionResult> LoginWithGoogle([FromBody] string credential)
        {
            var settings = new GoogleJsonWebSignature.ValidationSettings()
            {
                Audience = new List<string> { this._applicationSettings.GoogleClientId }
            };

            var payload = await GoogleJsonWebSignature.ValidateAsync(credential, settings);

            var UserList = await _service.GetAllAsync();

            var user = UserList.Where(x => x.Email == payload.Email).FirstOrDefault();

            if (user != null)
            {
                return Ok(JWTGenerator(AuthMapper.creatingToAuthDomain(user)));
            }
            else
            {
                return BadRequest();
            }
        }

        [HttpPatch("{id}")]
        public async Task<ActionResult<AuthDto>> AnonimizarConta(String id, Ativo ativo)
        {

            var utilizadores = await _service.GetAllAsync();
            IEnumerable<AuthDto> dto;
            try
            {
                dto = utilizadores.Where(c => c.Utilizador.Equals(id));
            }
            catch (Exception)
            {
                return NotFound();

            }


            var user = await _service.AnonimizarConta(dto.First(), ativo);

            if (user == null)
            {
                return NotFound();
            }

            return Ok(user);



        }
    }
}