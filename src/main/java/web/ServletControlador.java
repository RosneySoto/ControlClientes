package web;

import datos.ClienteDaoJDBC;
import dominio.Cliente;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ServletControlador")
public class ServletControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "editar":
                    this.editarCliente(request, response);
                    break;
                case "eliminar":
                    this.eliminarCliente(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
            }
        } else {
            this.accionDefault(request, response);
        }
    }

    private void accionDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Cliente> clientes = new ClienteDaoJDBC().listar();
        System.out.println("clientes = " + clientes);
        request.setAttribute("clientes", clientes);
        request.setAttribute("totalClientes", clientes.size());
        request.setAttribute("saldoTotal", this.calcularSaldoTotal(clientes));
        request.getRequestDispatcher("clientes.jsp").forward(request, response);
    }

    private double calcularSaldoTotal(List<Cliente> clientes) {
        double saldoTotal = 0;
        for (Cliente cliente : clientes) {
            saldoTotal += cliente.getSaldo();
        }
        return saldoTotal;
    }
    
    //Recupera el idCliente proprocionado en el JSP de listadoClientes, en la seccion de "Editar"
    private void editarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        Cliente cliente = new ClienteDaoJDBC().encontrar(new Cliente(idCliente));//Instanciamos el objeto cliente para luego llamar al metofo encontrar y crear un nuevo obejto de esta clase
        request.setAttribute("cliente", cliente);
        String jspEditar = "/WEB-INF/paginas/cliente/editarCliente.jsp";
        request.getRequestDispatcher(jspEditar).forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "insertar":
                    this.insertarCliente(request, response);
                    break;
                case "modificar":
                    this.modificarCliente(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
            }
        } else {
            this.accionDefault(request, response);
        }
    }

    private void insertarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Recuperamos los valores del formulario "agregar clientes"
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        double saldo = 0;
        String saldoString = request.getParameter("saldo");
        if (saldoString != null && !"".equals(saldoString)) {
            saldo = Double.parseDouble(saldoString);
        }

        //Creamos el objeto de cliente (modelo)
        Cliente cliente = new Cliente(nombre, apellido, email, telefono, saldo);

        //Insertamos en la base de datos
        int registrosModificados = new ClienteDaoJDBC().insertar(cliente);
        System.out.println("Registros Modificados = " + registrosModificados);

        //Redirigimos la pagina al metodo Default
        this.accionDefault(request, response);
    }
    
    private void modificarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Recuperamos los valores del formulario "editarClientes"
        
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        double saldo = 0;
        String saldoString = request.getParameter("saldo");
        if (saldoString != null && !"".equals(saldoString)) {
            saldo = Double.parseDouble(saldoString);
        }

        //Creamos el objeto de cliente (modelo)
        Cliente cliente = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);

        //Modificar el objeto en la base de datos
        int registrosModificados = new ClienteDaoJDBC().actualizar(cliente);
        System.out.println("Registros Modificados = " + registrosModificados);

        //Redirigimos la pagina al metodo Default
        this.accionDefault(request, response);
    }
    
    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Recuperamos los valores del formulario "editarClientes"
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        
        //Creamos el objeto de cliente (modelo)
        Cliente cliente = new Cliente(idCliente);

        //ELiminamos el objeto en la base de datos
        int registrosModificados = new ClienteDaoJDBC().eliminar(cliente);
        System.out.println("Registros Modificados = " + registrosModificados);

        //Redirigimos la pagina al metodo Default
        this.accionDefault(request, response);
    }   

}
