/**
 * @Description 
 * @author tangjun
 * @date 2016年8月17日
 * 
 */
package com.ff.common.util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;

import com.ff.common.service.model.FFExcelMeta;
import com.ff.common.service.model.FFExcelMetaAnno;
import com.ff.common.util.log.FFLogFactory;
import com.ff.common.util.meta.ReflectionUtil;

/**
 * @Description
 * @author tangjun
 * @date 2016年8月17日
 */
public class ExcelUtil
{
 	private static Logger log = FFLogFactory.getLog(ExcelUtil.class);

	public static List<FFExcelMeta> getExcelMeta(Class<?> clazz)
	{
		List<FFExcelMeta> metaList = new ArrayList<FFExcelMeta>();
		List<Field> fields = ReflectionUtil.getFieldsByAnnotion(clazz, FFExcelMetaAnno.class);
		for (Field field : fields)
		{
			FFExcelMeta meta = new FFExcelMeta();
			FFExcelMetaAnno anno = field.getAnnotation(FFExcelMetaAnno.class);
			meta.setField(field.getName());
			meta.setName(anno.name());
			metaList.add(meta);
		}
		return metaList;

	}
	public static File export(List<FFExcelMeta> metaList, List dataList)
	{
		return export(metaList,dataList,1);
	}

	public static File export(List<FFExcelMeta> metaList, List dataList, int type)
	{
		FileOutputStream o = null;
		File file = null;
		try
		{ // 创建工作薄
			Workbook workBook = null;
			String filepath = System.getProperty("java.io.tmpdir") + File.separatorChar + System.currentTimeMillis()
					+ "" + new Random().nextInt();
			if (type == 1)
			{
				workBook = new HSSFWorkbook();
				filepath += ".xls";
			} else
			{
				workBook = new SXSSFWorkbook(200);// 超过两百行放入就放入磁盘，否则在内存中
				filepath += ".xlsx";
			}
			file = new File(filepath);

			readInRows(workBook, metaList, dataList);
			o = new FileOutputStream(file);
			workBook.write(o);

		} catch (Exception ex)
		{
			log.error("导出时构造文件出错：" + ex.getMessage());
		} finally
		{
			try
			{
				if (null != o)
				{
					o.flush();
				}

			} catch (Exception e)
			{
				log.error(""+e);
			}
			try
			{
				if (null != o)
				{
					o.close();
				}

			} catch (Exception e)
			{
				log.error("close excel failed" + e);
			}
		}

		return file;
	}

	private static void readInRows(Workbook workBook, List<FFExcelMeta> metaList, List<Object> data)
	{
		try
		{
			Sheet sheet = null;

			sheet = workBook.createSheet();
			setHeadRow(workBook, sheet, metaList);
			setRowData(sheet, 1, metaList, data);

		} catch (Exception e)
		{
			log.error("" + e.getMessage());
		}
	}

	/**
	 * 创建列头
	 * 
	 * @param sheet
	 * @param headers
	 */
	private static void setHeadRow(Workbook workBook, Sheet sheet, List<FFExcelMeta> metaList)
	{
		// 创建第一行
		CellStyle style = workBook.createCellStyle();
		style.setFillForegroundColor((short) 17);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

		Row row = sheet.createRow(0);
		int i = 0;
		for (FFExcelMeta head : metaList)
		{
			Cell cell = row.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(head.getName());
			i++;
		}
	}
	
	/**
	 * 插入数据，返回最大行数
	 * 
	 * @param sheet
	 * @param properties
	 * @param data
	 * @return
	 */
	private static int setRowData(Sheet sheet, int rowIndex, List<FFExcelMeta> metaList, List<Object> data)
	{
		int i = rowIndex;
		for (Object bean : data)
		{

			Row row = sheet.createRow(i);

			int j = 0;
			for (FFExcelMeta meta : metaList)
			{
				try
				{
					Cell cell = row.createCell(j);
					String cellValue = "";
					Object value = ReflectionUtil.getValueByFieldName(bean, meta.getField());

					if (null != value)
					{
						cellValue = String.valueOf(value);
					}
					cell.setCellValue(cellValue);
				} catch (Exception e)
				{
					log.error("", e);
				}
				j++;
			}
			i++;
		}

		return i;
	}

}
