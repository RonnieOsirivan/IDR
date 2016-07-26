package th.ac.rbru.idr.util;

public class CourseName {
	private String courseName = "";
	public String getCourseName(int facultyId, int admitAcadYear,String programeName,String revisionCode){
		if(facultyId == 2){
			this.courseName = getCourseNameEducation(admitAcadYear, programeName);
		}else if(facultyId == 3){
			this.courseName = getCourseNameHuman(admitAcadYear, programeName);
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
				return "(หลักสูตร (ศศ.บ.) พ.ศ. ๒๕๕๒)";
			}
		}
		
		if(admitAcadYear >= 2549){
			if(programeName.contains("ศิลปกรรม") || programeName.contains("ออกแบบประยุกต์ศิลป์") )
			{
				return "(หลักสูตร (ศป.บ.) พ.ศ. ๒๕๔๙)";
			}
		}
		
		if(admitAcadYear >= 2549){
			if(programeName.contains("รัฐประศาสนศาสตร์") || programeName.contains("ปกครองท้องถิ่น") 
					|| programeName.contains("จัดการการคลัง"))
			{
				return "(หลักสูตร (รป.บ.) พ.ศ. ๒๕๔๙)";
			}
		}
		
		if(admitAcadYear >= 2549){
			if(programeName.contains("อังกฤษธุรกิจ") || programeName.contains("พัฒนาชุมชน") 
					|| programeName.contains("ดนตรี"))
			{
				return "(หลักสูตร (ศศ.บ.) พ.ศ. ๒๕๔๙)";
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
