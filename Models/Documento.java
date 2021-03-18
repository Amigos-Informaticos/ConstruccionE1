package Models;

import DAO.DAODocumento;
import tools.File;

public class Documento {
	private String title;
	private String type;
	private File file;
	private Usuario author;
	
	public Documento() {
	}
	
	public Documento(String title, String type, File file, Usuario author) {
		this.title = title;
		this.type = type;
		this.file = file;
		this.author = author;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}
	
	public Usuario getAuthor() {
		return author;
	}
	
	public void setAuthor(Usuario author) {
		this.author = author;
	}
	
	public boolean isComplete() {
		return this.title != null &&
			this.type != null;
	}
	
	public boolean save() {
		return new DAODocumento(this).save(this.author.getEmail());
	}
	
	public boolean saveLocally() {
		return new DAODocumento(this).saveLocally();
	}
	
	public boolean downloadFile() {
		return new DAODocumento(this).downloadFile();
	}
	
	/*public boolean generateAssignmentDocument(Assignment assignment) throws FileNotFoundException {
		assert assignment != null : "Assignment is null: Document.generateAssignmentDocument()";
		assert assignment.isComplete() :
			"Assignment is incomplete: Document.generateAssignmentDocument()";
		assert this.isComplete() : "Document is incomplete: Document.generateAssignmentDocument()";
		assert this.file.getPath() != null :
			"File path is null: Document.generateAssignmentDocument()";
		PdfWriter pdfWriter = new PdfWriter(file.getStringPath());
		PdfDocument pdfDocument = new PdfDocument(pdfWriter);
		com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDocument);
		String[] date = new String[3];
		date[0] = String.valueOf(LocalDate.now().getDayOfMonth());
		date[1] = LocalDate.now().getMonth().getDisplayName(
			TextStyle.FULL, new Locale("es", "ES"));
		date[2] = String.valueOf(LocalDate.now().getYear());
		String fullName = assignment.getStudent().getNames() + " " +
			assignment.getStudent().getLastnames();
		String regNumber = assignment.getStudent().getRegNumber();
		String proyectName = assignment.getProject().getName();
		document.add(
			new Paragraph(
				"Xalapa Enríquez, Veracruz, a " + date[0] + " de " + date[1] + " de " + date[2]
			).setTextAlignment(
				TextAlignment.RIGHT));
		document.add(new Paragraph(
			"Facultad de Estadística e Informática").setTextAlignment(TextAlignment.RIGHT));
		document.add(new Paragraph(
			"En atención a su solicitud expresada a la Coordinación de Prácticas " +
				"Profesionales de la Licenciatura en Ingeniería de Software, hacemos de su " +
				"conocimiento que el C." + fullName + " estudiante de la Licenciatura con " +
				"matrícula " + regNumber + ", ha sido asignado al proyecto" + proyectName +
				" a su digno cargo a partir del 13 de Agosto del presente hasta cubrir 200 horas. " +
				"Cabe mencionar que el estudiante cuenta con la formación y el perfil para las " +
				"actividades a desempeñar.").setTextAlignment(TextAlignment.JUSTIFIED));
		document.add(new Paragraph(
			"Anexo a este documento usted encontrará una copia del horario de las " +
				"experiencias educativas que el estudiante asignado se encuentra cursando para " +
				"que sea respetado y tomado en cuenta al momento de establecer el horario de " +
				"realización de sus prácticas profesionales. Por otra parte, le solicito de la " +
				"manera más atenta, haga llegar a la brevedad con el estudiante, el oficio de " +
				"aceptación así como el plan de trabajo detallado del estudiante, así como el " +
				"horario que cubrirá. Deberá indicar además, la forma en que se registrará la " +
				"evidencia de asistencia y número de horas cubiertas. Es importante mencionar " +
				"que el estudiante deberá presentar mensualmente un reporte de avances de sus " +
				"prácticas. Este reporte de avances puede entregarse hasta con una semana de " +
				"atraso por lo que le solicito de la manera más atenta sean elaborados y " +
				"avalados (incluyendo sello si aplica) de manera oportuna para su entrega al " +
				"Académico responsable de la experiencia de Prácticas de Ingeniería de Software. " +
				"En relación a lo anterior, es importante que en el oficio de aceptación " +
				"proporcione el nombre de la persona que supervisará y avalará en su dependencia " +
				"la prestación de las Prácticas profesionales así como número telefónico, " +
				"extensión (cuando aplique) y correo electrónico. Lo anterior con el fin de " +
				"contar con el canal de comunicación que permita dar seguimiento al desempeño " +
				"del estudiante.\n").setTextAlignment(TextAlignment.JUSTIFIED));
		document.add(new Paragraph(
			"Le informo que las prácticas de Ingeniería de Software forman parte de la " +
				"currícula de la Licenciatura en Ingeniería de Software, por lo cual es " +
				"necesaria su evaluación y de ahí la necesidad de realizar el seguimiento " +
				"correspondiente. Es por ello que, durante el semestre el Académico a cargo " +
				"de la experiencia educativa de prácticas de Ingeniería de Software realizará al " +
				"menos un seguimiento de las actividades del estudiante por lo que será " +
				"necesario mostrar evidencias de la asistencia del estudiante, así como de sus " +
				"actividades. Este seguimiento podrá ser vía correo electrónico, teléfono o " +
				"incluso mediante una visita a sus oficinas, por lo que le solicito de la manera " +
				"más atenta, proporcione las facilidades requeridas en su caso.\n")
			.setTextAlignment(TextAlignment.JUSTIFIED));
		document.add(new Paragraph(
			"Sin más por el momento, agradezco su atención al presente reiterándome a sus " +
				"apreciables órdenes."));
		Paragraph sign = new Paragraph("___________________________");
		sign.setTextAlignment(TextAlignment.CENTER);
		Paragraph coordinatorName = new Paragraph("Angel Juan Snachez Garcia");
		coordinatorName.setTextAlignment(TextAlignment.CENTER);
		Paragraph coordinator = new Paragraph("Coordinador de Prácticas Profesionales");
		coordinator.setTextAlignment(TextAlignment.CENTER);
		document.add(sign);
		document.add(coordinatorName);
		document.add(coordinator);
		document.flush();
		document.close();
		return true;
	}*/
}
