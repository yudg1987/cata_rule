package ${basePackage}.controller;

import com.iflytek.rule.common.SuccessJsonResult;
import ${basePackage}.entity.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by ${author} on ${date}.
*/
@RestController
@RequestMapping(value = "${baseRequestMapping}", produces = "application/json; charset=UTF-8")
public class ${modelNameUpperCamel}Controller {
    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @PostMapping
    public SuccessJsonResult<Void> add(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.save(${modelNameLowerCamel});
        return new SuccessJsonResult<Void>();
    }

    @DeleteMapping("/{id}")
    public SuccessJsonResult<Void> delete(@PathVariable Integer id) {
        ${modelNameLowerCamel}Service.deleteById(id);
        return new SuccessJsonResult<Void>();
    }

    @PutMapping
    public SuccessJsonResult<Void> update(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
        return new SuccessJsonResult<Void>();
    }

    @GetMapping("/{id}")
    public SuccessJsonResult<${modelNameUpperCamel}> detail(@PathVariable Integer id) {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.findById(id);
        return new SuccessJsonResult<${modelNameUpperCamel}>(${modelNameLowerCamel});
    }

    @GetMapping
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public SuccessJsonResult<PageInfo> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return new SuccessJsonResult<PageInfo>(pageInfo);
    }
}
