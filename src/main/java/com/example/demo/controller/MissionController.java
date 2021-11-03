package com.example.demo.controller;

import com.example.demo.entity.Center;
import com.example.demo.entity.Mission;
import com.example.demo.entity.Model;
import com.example.demo.service.CenterService;
import com.example.demo.service.MissionService;
import com.example.demo.service.ModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.*;


/**
 * (Mission)表控制层
 *
 * @author makejava
 * @since 2021-09-08 13:28:34
 */
@Api(tags = "Excel管理")
@Controller
@RequestMapping("/Mission")
public class MissionController {
    /**
     * 服务对象
     */
    @Resource
    private MissionService missionService;
    @Resource
    private ModelService modelService;
    @Resource
    private CenterService centerService;

    /**
     * 从数据库中拿出数据
     *
     * @return
     */
    public List<Object[]> exportData(List<Mission> list) {
        List<Object[]> lists = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Model model = modelService.queryById(Integer.valueOf(list.get(i).getModelId()));
            Center center = centerService.queryById(Integer.valueOf(model.getCenterId()));
            Object[] objects = new Object[13];
            objects[0] = center.getCenterName();
            objects[1] = model.getModelName();
            objects[2] = list.get(i).getDirector();
            objects[3] = list.get(i).getOppositePerson();
            objects[4] = list.get(i).getMissionName();
            objects[5] = list.get(i).getDescribe();
            objects[6] = list.get(i).getStarttime();
            objects[7] = list.get(i).getEndtime();
            objects[8] = list.get(i).getTimechange();
            objects[9] = list.get(i).getChangreason();
            objects[10] = list.get(i).getSpeedOfProgress();
            objects[11] = list.get(i).getRelatedDocumentLinks();
            objects[12] = list.get(i).getAcceptancePassed();
            lists.add(objects);
        }
        return lists;
    }


    /**
     * 塞入数据
     *
     * @param sheet
     */
    private void setData(HSSFSheet sheet,List<Mission> list) {
        try {
            List<Object[]> data = exportData(list);
            int rowNum = 1;
            for (int i = 0; i < data.size(); i++) {
                HSSFRow row = sheet.createRow(rowNum);
                for (int j = 0; j < data.get(i).length; j++) {
                    row.createCell(j).setCellValue((String) data.get(i)[j]);
                }
                rowNum++;
            }
            System.out.println("表格赋值成功！");
        } catch (Exception e) {
            System.out.println("表格赋值失败！");
            e.printStackTrace();
        }
    }

    /**
     * 设置title
     */
    private void setTitle(HSSFWorkbook workbook, HSSFSheet sheet) {
        try {
            String[] str = new String[]{"中台模块","中台模块","负责人", "后端对接人", "任务名称"
                    , "描述", "开始时间", "结束时间", "计划截止时间变更", "变更原因", "进度", "相关文档", "验证通过"};
            HSSFRow row = sheet.createRow(0);
            //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
            for (int i = 0; i <= str.length; i++) {
                sheet.setColumnWidth(i, 15 * 256);
            }
            //设置为居中加粗,格式化时间格式
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
            //创建表头名称
            HSSFCell cell;

            for (int j = 0; j < str.length; j++) {
                cell = row.createCell(j);
                cell.setCellValue(str[j]);
                cell.setCellStyle(style);
            }
        } catch (Exception e) {
            System.out.println("导出时设置表头失败！");
            e.printStackTrace();
        }
    }
    @RequestMapping("/showExcel")
    public String show(){
        return "/Excel.html";
    }



   @ApiOperation("导出所有任务")
   @RequestMapping(value = "/exportExcel",method =RequestMethod.GET)
    public void exportExcel(HttpServletResponse response) {
        try {
            //实例化HSSFWorkbook
            HSSFWorkbook workbook = new HSSFWorkbook();
            //创建一个Excel表单，参数为sheet的名字
            HSSFSheet sheet = workbook.createSheet("sheet");
            //设置表头
            setTitle(workbook, sheet);
            //设置单元格并赋值
            setData(sheet,missionService.queryAllMissions());
            //设置浏览器下载
            try {
                //清空response
                response.reset();
                //设置response的Header
                response.addHeader("Content-Disposition", "attachment;filename=" + "sheet");
                OutputStream os = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/vnd.ms-excel;charset=gb2312");
                //将excel写入到输出流中
                workbook.write(os);
                os.flush();
                os.close();
                System.out.println("设置浏览器下载成功！");
            } catch (Exception e) {
                System.out.println("设置浏览器下载失败！");
                e.printStackTrace();
            }
            System.out.println("导出解析成功!");
        } catch (Exception e) {
            System.out.println("导出解析失败!");
            e.printStackTrace();
        }
    }

    @ApiOperation("导出本周任务")
    @RequestMapping(value = "/exportNowExcel",method =RequestMethod.GET)
    public void exportNowExcel(HttpServletResponse response) {
        try {
            //实例化HSSFWorkbook
            HSSFWorkbook workbook = new HSSFWorkbook();
            //创建一个Excel表单，参数为sheet的名字
            HSSFSheet sheet = workbook.createSheet("每周周报");
            //设置表头
            setTitle(workbook, sheet);
            //设置单元格并赋值
            setData(sheet,NowWeekMission());
            //设置浏览器下载
            try {
                //清空response
                response.reset();
                //设置response的Header
                response.addHeader("Content-Disposition", "attachment;filename=" + "sheet");
                OutputStream os = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/vnd.ms-excel;charset=gb2312");
                //将excel写入到输出流中
                workbook.write(os);
                os.flush();
                os.close();
                System.out.println("设置浏览器下载成功！");
            } catch (Exception e) {
                System.out.println("设置浏览器下载失败！");
                e.printStackTrace();
            }
            System.out.println("导出解析成功!");
        } catch (Exception e) {
            System.out.println("导出解析失败!");
            e.printStackTrace();
        }
    }
    /**
     *
     * 导入数据
     */
    public List<List<String>> importExcel(String fileName) {
        try {
            List<List<String>> list = new ArrayList<>();
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            //获取sheet的行数
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < rows; i++) {
                //过滤表头行
                if (i == 0) {
                    continue;
                }
                //获取当前行的数据
                HSSFRow row = sheet.getRow(i);

               List<String> objects = new ArrayList<>();
                for (int j=0;j<13;j++) {
                    HSSFCell cell=row.getCell(j);
                    if(cell==null||cell.getCellType().equals(BLANK)){
                        objects.add(null);
                        continue;
                    }
                    if (cell.getCellType().equals(NUMERIC)) {
                        objects.add(String.valueOf(cell.getNumericCellValue()));
                    }
                    if (cell.getCellType().equals(_NONE)) {
                        objects.add(cell.getStringCellValue());
                    }
                    if (cell.getCellType().equals(BLANK)) {
                        objects.add(cell.getStringCellValue());
                    }
                    if (cell.getCellType().equals(STRING)) {
                        objects.add(cell.getStringCellValue());
                    }
                    if (cell.getCellType().equals(BOOLEAN)) {
                        objects.add(String.valueOf(cell.getBooleanCellValue()));
                    }
                    if (cell.getCellType().equals(ERROR)) {
                        objects.add(String.valueOf(cell.getErrorCellValue()));
                    }
                }
                list.add(objects);
            }
            System.out.println("导入文件解析成功！");
            return list;
        }catch (Exception e){
            System.out.println("导入文件解析失败！");
            e.printStackTrace();
        }
        return null;
    }
    /**
     *
     * 更新数据库
     */
    @ApiOperation("修改任务")
//    @ApiImplicitParams({@ApiImplicitParam(name = "filename",value="文件地址",required = true,dataType = "String")})
    @RequestMapping(value = "/importExcel",method =RequestMethod.GET)
    public void updateDb(){
        List<List<String>> list=importExcel("C:\\Users\\chenyanlong\\Downloads\\sheet.xls");
        for(int i=0;i<list.size();i++){
            Mission mission=new Mission();
            Model model=modelService.queryBymodel_name(list.get(i).get(1));
            mission.setModelId(String.valueOf( model.getId()));
            mission.setModel( list.get(i).get(1));
            mission.setDirector(list.get(i).get(2));
            mission.setOppositePerson(list.get(i).get(3));
            mission.setMissionName(list.get(i).get(4));
            mission.setDescribe(list.get(i).get(5));
            mission.setStarttime(list.get(i).get(6));
            mission.setEndtime(list.get(i).get(7));
            mission.setTimechange(list.get(i).get(8));
            mission.setChangreason(list.get(i).get(9));
            mission.setSpeedOfProgress(list.get(i).get(10));
            mission.setRelatedDocumentLinks(list.get(i).get(11));
            mission.setAcceptancePassed(list.get(i).get(12));
            missionService.updateOrinsert(mission);
        }
    }
    /**
     * 获取当前时间所在周的周一和周日的日期时间
     * @return
     */
    public  Map<String,String> getWeekDate() {
        Map<String,String> map = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Calendar cal = Calendar.getInstance();
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if(dayWeek==1){
            dayWeek = 8;
        }

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        Date mondayDate = cal.getTime();
        String weekBegin = sdf.format(mondayDate);

        cal.add(Calendar.DATE, 4 +cal.getFirstDayOfWeek());
        Date sundayDate = cal.getTime();
        String weekEnd = sdf.format(sundayDate);

        map.put("mondayDate", weekBegin);
        map.put("sundayDate", weekEnd);
        return map;
    }


    /**
     *
     * 获取当前周任务
     */
    public List<Mission> NowWeekMission(){
        Map<String,String> map=getWeekDate();
        String date1=map.get("mondayDate");
        String date2=map.get("sundayDate");
        List<Mission> missionList=missionService.queryAllByDate(date1,date2);
        return missionList;
    }
}


