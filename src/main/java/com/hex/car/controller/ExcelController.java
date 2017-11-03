package com.hex.car.controller;

import com.hex.car.domain.Car;
import com.hex.car.domain.CarType;
import com.hex.car.domain.Parameter;
import com.hex.car.service.*;
import com.hex.car.utils.ResultUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: hexuan
 * Date: 2017/10/30
 * Time: 上午10:36
 */
@RestController
public class ExcelController {

    @Autowired
    private CarService carService;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private CarTypeService carTypeService;

    @PostMapping(value = "/importCarExcel")
    public Object importCarExcel(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Workbook wb = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = wb.getSheetAt(0);
        Row row;
        Cell cell;
        Car car;
        List<Car> cars = new ArrayList<>();
        Map<String, Parameter> parameterMap = new HashMap<>();
        for (Parameter parameter : parameterService.findParametersByStateOrderBySort(new Integer(2))) {
            parameterMap.put(parameter.getName(), parameter);
        }
        Map<String, CarType> carTypeMap = new HashMap<>();
        for (CarType carType : carTypeService.findCarTypesByStateOrderBySort(new Integer(2))) {
            carTypeMap.put(carType.getName(), carType);
        }
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 跳过第一行标题行，所以i从1开始
            row = sheet.getRow(i);
            car = new Car();
            for (int j = 0; j <= row.getLastCellNum(); j++) {
                cell = row.getCell(j);
                if (cell != null) {
                    String cellString = getCellValue(cell).trim();
                    switch (j) {
                        case 1:
                            car.setName(cellString);
                            break;
                        case 2:
                            if (!cellString.equals("")) {
                                car.setDisplacement(Double.parseDouble(cellString));
                            }
                            break;
                        case 3:
                            if (!cellString.trim().equals(""))
                                car.setYear(Integer.parseInt(cellString));
                            break;
                        case 4:
                            car.setBrand(brandService.findFirstByName(cellString));
                            break;
                        case 5:
                            car.setModel(modelService.findFirstByName(cellString));
                            break;
                        case 6:
                            car.setCarType(carTypeMap.get(cellString));
                            break;
                        case 7:
                            car.setEngineType(parameterMap.get(cellString));
                            break;
                        case 8:
                            car.setDrivetrain(parameterMap.get(cellString));
                            break;
                        case 9:
                            car.setTransmission(parameterMap.get(cellString));
                            break;
                        case 10:
                            car.setFuelType(parameterMap.get(cellString));
                            break;
                        case 11:
                            car.setBodyType(parameterMap.get(cellString));
                            break;
                        case 12:
                            car.setSeats(parameterMap.get(cellString));
                            break;
                    }
                }
            }
            cars.add(car);
        }
        carService.saveCarList(cars);
        return ResultUtil.success();
    }

    protected static String getCellValue(Cell cell) {
        String cellValue = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = sdf.format(cell.getDateCellValue());
                    } else {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        String temp = cell.getStringCellValue();
                        // 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
                        if (temp.indexOf(".") > -1) {
                            cellValue = String.valueOf(new Double(temp)).trim();
                        } else {
                            cellValue = temp.trim();
                        }
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cellValue = cell.getCellFormula();
                    break;
                default:
                    cellValue = "";
            }
        }
        return cellValue;
    }
}
