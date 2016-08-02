package th.ac.rbru.idr.util;

public class CourseName {
	private String courseName = "";
	public String getCourseName(int facultyId, int admitAcadYear,String programeName,String revisionCode){
		if(facultyId == 1){
			this.courseName = getCoureNameScience(admitAcadYear, programeName);
		}else if(facultyId == 2){
			this.courseName = getCourseNameEducation(admitAcadYear, programeName);
		}else if(facultyId == 3){
			this.courseName = getCourseNameHuman(admitAcadYear, programeName);
		}else if(facultyId == 4){
			this.courseName = getCourseNameManagement(admitAcadYear, programeName);
		}else if(facultyId == 5){
			this.courseName = getCourseNameAgricultural(admitAcadYear, programeName);
		}else if(facultyId == 6){
			this.courseName = getCourseNameIndustria(admitAcadYear, programeName);
		}else if(facultyId == 7){
			this.courseName = getCourseNameLaw(admitAcadYear, programeName);
		}else if(facultyId == 8){
			this.courseName = getCourseNameComputer(admitAcadYear, programeName);
		}else if(facultyId == 9){
			this.courseName = getCourseNameArts(admitAcadYear, programeName);
		}else if(facultyId == 10){
			this.courseName = getCourseNameGemological(admitAcadYear, programeName);
		}
		return courseName;
	}
	
	private String getCourseNameEducationByRevision(String revisionCode){
		if("54".equals(revisionCode)){
			return "(หลักสูตร พ.ศ. ๒๕๕๒)";
		}else if("52".equals(revisionCode)){
			return "(หลักสูตร พ.ศ. ๒๕๕๒)";
		}else if("50".equals(revisionCode)){
			return "(หลักสูตร พ.ศ. ๒๕๕๐)";
		}else if("49".equals(revisionCode)){
			return "(หลักสูตร พ.ศ. ๒๕๔๙)";
		}else if("46".equals(revisionCode)){
			return "(หลักสูตรใหม่ พ.ศ. ๒๕๔๖)";
		}else if("43".equals(revisionCode)){
			return "(หลักสูตรสถาบันราชภัฏ พ.ศ. ๒๕๔๓)";
		}
		return "";
	}
	
	private String getCoureNameScience(int admitAcadYear,String programeName){
		if(admitAcadYear >= 2555){
			if(programeName.contains("คหกรรมศาสตร์") || programeName.contains("เคมี") 
					|| programeName.contains("สถิติประยุกต์") || programeName.contains("สิ่งแวดล้อม")){
				return "(หลักสูตรปรับปรุง วท.บ. ๒๕๕๕)";
			}
		}
		
		if(admitAcadYear >= 2549){
			if(programeName.contains("สถิติประยุกต์") || programeName.contains("สิ่งแวดล้อม")
					|| programeName.contains("คหกรรมศาสตร์") || programeName.contains("เคมี")
					|| programeName.contains("ชีววิทยาประยุกต์") || programeName.contains("ฟิสิกส์")){
				return "(หลักสูตร วท.บ. พ.ศ. ๒๕๔๙)";
			}
		}
		
		if(admitAcadYear >= 2546){
			return "(หลักสูตรใหม่ พ.ศ. ๒๕๔๖)";
		}
		
		if(admitAcadYear >= 2544){
			return "(หลักสูตรสถาบันราชภัฏ พ.ศ. ๒๕๔๓)";
		}
		return "";
	}
	
	private String getCourseNameEducation(int admitAcadYear,String programeName){
		if(admitAcadYear >= 2554){
			if(programeName.contains("การศึกษาปฐมวัย") || programeName.contains("คณิตศาสตร์") || programeName.contains("คอมพิวเตอร์ศึกษา") 
					|| programeName.contains("พลศึกษา") || programeName.contains("ภาษาไทย") || programeName.contains("ภาษาอังกฤษ")
					|| programeName.contains("วิทยาศาสตร์") || programeName.contains("สังคมศึกษา") || programeName.contains("ศึกษาพิเศษ"))
			{
				return "(หลักสูตรปรับปรุง พ.ศ. ๒๕๕๔)";
			}
		}
		
		if(admitAcadYear >= 2552){
			if(programeName.contains("วิทยาศาสตร์") || programeName.contains("ภาษาอังกฤษ") || programeName.contains("ภาษาไทย") )
			{
				return "(หลักสูตร พ.ศ. ๒๕๕๒)";
			}
		}
		
		if(admitAcadYear >= 2550){
			if(programeName.contains("การศึกษาพิเศษ") || programeName.contains("สังคมศึกษา") )
			{
				return "(หลักสูตร พ.ศ. ๒๕๕๐)";
			}
		}
		
		if(admitAcadYear >= 2549){
			if(programeName.contains("การศึกษาปฐมวัย") || programeName.contains("คณิตศาสตร์") 
					|| programeName.contains("พลศึกษา") || programeName.contains("วิทยาศาสตร์ทั่วไป") )
			{
				return "(หลักสูตร พ.ศ. ๒๕๔๙)";
			}
		}
		
		if(admitAcadYear >= 2546){
			return "(หลักสูตรใหม่ พ.ศ. ๒๕๔๖)";
		}
		
		if(admitAcadYear >= 2544){
			return "(หลักสูตรสถาบันราชภัฏ พ.ศ. ๒๕๔๓)";
		}
		return "";
	}
	
	private String getCourseNameHuman(int admitAcadYear,String programeName){
		if(admitAcadYear >= 2555){
			if(programeName.contains("ดนตรี") || programeName.contains("รัฐศาสตร์") || programeName.contains("พัฒนาชุมชน") 
					|| programeName.contains("รัฐประศาสนศาสตร์") || programeName.contains("ภาษาไทย") || programeName.contains("ภาษาอังกฤษธุรกิจ")
					|| programeName.contains("ภาษาจีน"))
			{
				return "(หลักสูตรปรับปรุง พ.ศ. ๒๕๕๕)";
			}
		}
		
		if(admitAcadYear >= 2552){
			if(programeName.contains("ภาษาจีน"))
			{
				return "(หลักสูตร ศศ.บ. พ.ศ. ๒๕๕๒)";
			}
		}
		
		if(admitAcadYear >= 2549){
			if(programeName.contains("ศิลปกรรม") || programeName.contains("ออกแบบประยุกต์ศิลป์") )
			{
				return "(หลักสูตร ศป.บ. พ.ศ. ๒๕๔๙)";
			}
		}
		
		if(admitAcadYear >= 2549){
			if(programeName.contains("รัฐประศาสนศาสตร์") || programeName.contains("ปกครองท้องถิ่น") 
					|| programeName.contains("จัดการการคลัง"))
			{
				return "(หลักสูตร รป.บ. พ.ศ. ๒๕๔๙)";
			}
		}
		
		if(admitAcadYear >= 2549){
			if(programeName.contains("อังกฤษธุรกิจ") || programeName.contains("พัฒนาชุมชน") 
					|| programeName.contains("ดนตรี"))
			{
				return "(หลักสูตร ศศ.บ. พ.ศ. ๒๕๔๙)";
			}
		}
		
		if(admitAcadYear >= 2546){
			return "(หลักสูตรใหม่ พ.ศ. ๒๕๔๖)";
		}
		
		if(admitAcadYear >= 2544){
			return "(หลักสูตรสถาบันราชภัฏ พ.ศ. ๒๕๔๓)";
		}
		return "";
	}
	
	private String getCourseNameManagement(int admitAcadYear,String programeName){
		if(admitAcadYear >= 2555){
			if(programeName.contains("ท่องเที่ยว") || programeName.contains("คอมพิวเตอร์ธุรกิจ") || programeName.contains("การเงินการธนาคาร") 
					|| programeName.contains("เศรษฐศาสตร์ธุรกิจ") || programeName.contains("บริหารทรัพยากรมนุษย์") || programeName.contains("การตลาด")
					|| programeName.contains("การจัดการ") || programeName.contains("บัญชีบัณฑิต")){
				return "(หลักสูตรปรับปรุง พ.ศ. ๒๕๕๕)";
			}
		}
		
		if(admitAcadYear >= 2549){
			if(programeName.contains("คอมพิวเตอร์ธุรกิจ") || programeName.contains("การจัดการ") || programeName.contains("เศรษฐศาสตร์") 
					|| programeName.contains("เศรษฐศาสตร์") || programeName.contains("อุตสาหกรรมท่องเที่ยว") || programeName.contains("การเงินการธนาคาร")
					|| programeName.contains("การตลาด") || programeName.contains("บริหารทรัพยากรมนุษย์") || programeName.contains("การบัญชี")){
				return "(หลักสูตร พ.ศ. ๒๕๔๙)";
			}
		}
		
		if(admitAcadYear >= 2546){
			return "(หลักสูตรใหม่ พ.ศ. ๒๕๔๖)";
		}
		
		if(admitAcadYear >= 2544){
			return "(หลักสูตรสถาบันราชภัฏ พ.ศ. ๒๕๔๓)";
		}
		return "";
	}
	
	private String getCourseNameAgricultural(int admitAcadYear,String programeName){
		if(admitAcadYear >= 2555){
			if(programeName.contains("อาหาร") || programeName.contains("เกษตรศาสตร์") || programeName.contains("เทคโนโลยีการเกษตร")
					|| programeName.contains("เพาะเลี้ยงสัตว์น้ำ")){
				return "(หลักสูตรปรับปรุง พ.ศ. ๒๕๕๕)";
			}
		}
		
		if(admitAcadYear >= 2549){
			if(programeName.contains("เทคโนโลยีการเกษตร")){
				return "(หลักสูตร ทล.บ. พ.ศ. ๒๕๔๙)";
			}
		}
		
		if(admitAcadYear >= 2549){
			if(programeName.contains("เกษตรศาสตร์") || programeName.contains("การเพาะเลี้ยง") || programeName.contains("เทคโนโลยีการอาหาร")){
				return "(หลักสูตร วท.บ. ๒๕๔๙)";
			}
		}
		
		if(admitAcadYear >= 2546){
			return "(หลักสูตรใหม่ พ.ศ. ๒๕๔๖)";
		}
		
		if(admitAcadYear >= 2544){
			return "(หลักสูตรสถาบันราชภัฏ พ.ศ. ๒๕๔๓)";
		}
		
		return "";
	}
	
	private String getCourseNameIndustria(int admitAcadYear,String programeName){
		if(admitAcadYear >= 2556){
			if(programeName.contains("เทคโนโลยีอุตสาหกรรม (ต่อเนื่อง)")){
				return "(หลักสูตรใหม่ ทล.บ. พ.ศ. ๒๕๕๖)";
			}
		}
		
		if(admitAcadYear >= 2555){
			if(programeName.contains("วิศวกรรมโลจิสติกและการจัดการ") || programeName.contains("วิศวกรรมแมคคาทรอนิกส์") 
				|| programeName.contains("วิศวกรรมสารสนเทศและการสื่อสาร") || programeName.contains("วิศวกรรมโยธา")){
				return "(หลักสูตรปรับปรุง วศ.บ. พ.ศ. ๒๕๕๕)";
			}
		}
		
		if(admitAcadYear >= 2552){
			if(programeName.contains("วิศวกรรมสารสนเทศและการสื่อสาร") || programeName.contains("วิศวกรรมโยธา") || programeName.contains("วิศวกรรมแมคคาทรอนิกส์")){
				return "(หลักสูตร วศ.บ. พ.ศ. ๒๕๕๒)";
			}
		}
		
		if(admitAcadYear >= 2550){
			if(programeName.contains("วิศวกรรมโลจิสติกส์และการจัดการ")){
				return "(หลักสูตร พ.ศ. ๒๕๕๐)";
			}
		}
		
		if(admitAcadYear >= 2549){
			if(programeName.contains("เทคโนโลยีอุตสาหกรรม")){
				return "(หลักสูตร พ.ศ. ๒๕๔๙)";
			}
		}
		
		if(admitAcadYear >= 2546){
			return "(หลักสูตรใหม่ พ.ศ. ๒๕๔๖)";
		}
		
		if(admitAcadYear >= 2544){
			return "(หลักสูตรสถาบันราชภัฏ พ.ศ. ๒๕๔๓)";
		}
		return "";
	}
	
	private String getCourseNameLaw(int admitAcadYear,String programeName){
		if(admitAcadYear >= 2556){
			if(programeName.contains("นิติศาสตร์")){
				return "(หลักสูตรปรับปรุง พ.ศ. ๒๕๕๕)";
			}
		}
		
		if(admitAcadYear >= 2549){
			if(programeName.contains("นิติศาสตร์")){
				return "(หลักสูตร น.บ. พ.ศ. ๒๕๔๙)";
			}
		}
		
		if(admitAcadYear >= 2546){
			return "(หลักสูตรใหม่ พ.ศ. ๒๕๔๖)";
		}
		
		if(admitAcadYear >= 2544){
			return "(หลักสูตรสถาบันราชภัฏ พ.ศ. ๒๕๔๓)";
		}
		return "";
	}
	
	private String getCourseNameComputer(int admitAcadYear,String programeName){
		if(admitAcadYear >= 2555){
			if(programeName.contains("ภูมิสารสนเทศ") || programeName.contains("เครือข่ายคอมพิวเตอร์")
				|| programeName.contains("เทคโนโลยีสารสนเทศ") || programeName.contains("วิทยาการคอมพิวเตอร์")){
				return "(หลักสูตรปรับปรุง วท.บ. พ.ศ. ๒๕๕๕)";
			}
		}
		
		if(admitAcadYear >= 2552){
			if(programeName.contains("ภูมิสารสนเทศ") || programeName.contains("เครือข่ายคอมพิวเตอร์")){
				return "(หลักสูตรใหม่ วท.บ. พ.ศ. ๒๕๕๒)";
			}
		}
		
		if(admitAcadYear >= 2549){
			if(programeName.contains("เทคโนโลยีสารสนเทศ") || programeName.contains("วิทยาการคอมพิวเตอร์")){
				return "(หลักสูตร วท.บ. พ.ศ. ๒๕๔๙)";
			}
		}
		
		if(admitAcadYear >= 2546){
			return "(หลักสูตรใหม่ พ.ศ. ๒๕๔๖)";
		}
		
		if(admitAcadYear >= 2544){
			return "(หลักสูตรสถาบันราชภัฏ พ.ศ. ๒๕๔๓)";
		}
		return "";
	}
	
	private String getCourseNameArts(int admitAcadYear,String programeName){
		if(admitAcadYear >= 2555){
			if(programeName.contains("สื่อสารบูรณาการ")){
				return "(หลักสูตรปรับปรุง นศ.บ. พ.ศ. ๒๕๕๕)";
			}
		}
		
		if(admitAcadYear >= 2552){
			if(programeName.contains("นิเทศศาสตร์")){
				return "(หลักสูตร นศ.บ. พ.ศ. ๒๕๕๒)";
			}
		}
		
		if(admitAcadYear >= 2549){
			if(programeName.contains("นิเทศศาสตร์")){
				return "(หลักสูตร ศศ.บ. พ.ศ. ๒๕๔๙)";
			}
		}
		
		if(admitAcadYear >= 2546){
			return "(หลักสูตรใหม่ พ.ศ. ๒๕๔๖)";
		}
		
		if(admitAcadYear >= 2544){
			return "(หลักสูตรสถาบันราชภัฏ พ.ศ. ๒๕๔๓)";
		}
		return "";
	}
	
	private String getCourseNameGemological(int admitAcadYear,String programeName){
		if(admitAcadYear >= 2555){
			if(programeName.contains("การออกแบบ") || programeName.contains("อัญมณีศาสตร์") || programeName.contains("ออกแบบผลิตภัณฑ์")){
				return "(หลักสูตรปรับปรุง พ.ศ. ๒๕๕๕)";
			}
		}
		
		if(admitAcadYear >= 2552){
			if(programeName.contains("ออกแบบผลิตภัณฑ์")){
				return "(หลักสูตร ศป.บ. พ.ศ. ๒๕๕๒)";
			}
		}
		
		if(admitAcadYear >= 2550){
			if(programeName.contains("อัญมณีศาสตร์")){
				return "(หลักสูตร วท.บ. พ.ศ. ๒๕๕๐)";
			}
		}
		
		if(admitAcadYear >= 2549){
			if(programeName.contains("ออกแบบผลิตภัณฑ์อุตสาหกรรม")){
				return "(หลักสูตร วท.บ. พ.ศ. ๒๕๔๙)";
			}
		}
		
		if(admitAcadYear >= 2546){
			return "(หลักสูตรใหม่ พ.ศ. ๒๕๔๖)";
		}
		
		if(admitAcadYear >= 2544){
			return "(หลักสูตรสถาบันราชภัฏ พ.ศ. ๒๕๔๓)";
		}
		return "";
	}
}