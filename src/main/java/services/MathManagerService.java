package services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import models.Alumno;
import models.Instituto;
import models.Operacion;
import repositories.MathManager;
import repositories.MathManagerImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.NoSuchElementException;

@Api(value = "/math", description = "Endpoint to Math Manager Service")
@Path("/math")
public class MathManagerService {

    private final MathManager mm;

    public MathManagerService() {
        this.mm = MathManagerImpl.getInstance();
    }

    @POST
    @Path("/institutos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add instituto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Instituto.class),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public Response addInstituto(Instituto instituto) {
        if (instituto == null || instituto.getId() == null || instituto.getNombre() == null) {
            return Response.status(400).entity(instituto).build();
        }

        this.mm.addInstituto(instituto);
        return Response.status(201).entity(instituto).build();
    }

    @POST
    @Path("/alumnos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add alumno")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Alumno.class),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public Response addAlumno(Alumno alumno) {
        if (alumno == null || alumno.getId() == null || alumno.getNombre() == null || alumno.getInstitutoId() == null) {
            return Response.status(400).entity(alumno).build();
        }

        this.mm.addAlumno(alumno);
        return Response.status(201).entity(alumno).build();
    }

    @POST
    @Path("/operaciones")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Solicitar operacion")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Operacion.class),
            @ApiResponse(code = 400, message = "Validation error"),
            @ApiResponse(code = 404, message = "Alumno or instituto not found")
    })
    public Response solicitarOperacion(Operacion operacion) {
        if (operacion == null || operacion.getId() == null || operacion.getExpresion() == null
                || operacion.getAlumnoId() == null || operacion.getInstitutoId() == null) {
            return Response.status(400).entity(operacion).build();
        }

        try {
            this.mm.solicitarOperacion(operacion);
            return Response.status(201).entity(operacion).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400).entity(e.getMessage()).build();
        } catch (NoSuchElementException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/operaciones/procesar")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Procesar siguiente operacion")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Operacion.class),
            @ApiResponse(code = 404, message = "No pending operations")
    })
    public Response procesarOperacion() {
        try {
            Operacion operacion = this.mm.procesarOperacion();
            return Response.status(200).entity(operacion).build();
        } catch (NoSuchElementException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/institutos/{institutoId}/operaciones")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get operaciones by instituto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Operacion.class, responseContainer = "List")
    })
    public Response getOperacionesByInstituto(@PathParam("institutoId") String institutoId) {
        List<Operacion> operaciones = this.mm.getOperacionesByInstituto(institutoId);
        GenericEntity<List<Operacion>> entity = new GenericEntity<List<Operacion>>(operaciones) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @Path("/alumnos/{alumnoId}/operaciones")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get operaciones by alumno")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Operacion.class, responseContainer = "List")
    })
    public Response getOperacionesByAlumno(@PathParam("alumnoId") String alumnoId) {
        List<Operacion> operaciones = this.mm.getOperacionesByAlumno(alumnoId);
        GenericEntity<List<Operacion>> entity = new GenericEntity<List<Operacion>>(operaciones) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @Path("/institutos/ranking")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get institutos ordered by number of operations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Instituto.class, responseContainer = "List")
    })
    public Response getInstitutosByNumOperaciones() {
        List<Instituto> institutos = this.mm.getInstitutosByNumOperaciones();
        GenericEntity<List<Instituto>> entity = new GenericEntity<List<Instituto>>(institutos) {};
        return Response.status(200).entity(entity).build();
    }
}
