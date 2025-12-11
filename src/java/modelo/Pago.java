    package modelo;

    // Importamos java.sql.Date o String, dependiendo de cómo manejes las fechas
    // Usaremos String para mantener la consistencia con tu Movimiento.java anterior
    public class Pago {
        private int id_pago;
        private int id_deuda; 
        private int abono; 
        private String fecha;

        // Constructor vacío (necesario para la persistencia)
        public Pago() {
        }

        // --- Getters y Setters ---

        public int getId_pago() {
            return id_pago;
        }

        public void setId_pago(int id_pago) {
            this.id_pago = id_pago;
        }

        public int getId_deuda() {
            return id_deuda;
        }

        public void setId_deuda(int id_deuda) {
            this.id_deuda = id_deuda;
        }

        public int getAbono() {
            return abono;
        }

        public void setAbono(int abono) {
            this.abono = abono;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }
    }