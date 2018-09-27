package io.choerodon.gitlab.api.controller.v1;

import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.ProjectHook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.app.service.HookService;


@RestController
@RequestMapping("/v1/hook")
public class HookController {

    private HookService hookService;

    public HookController(HookService hookService) {
        this.hookService = hookService;
    }

    /**
     * 创建ProjectHook对象
     *
     * @param projectId   项目id
     * @param userId      用户Id
     * @param projectHook projectHook对象
     * @return ProjectHook
     */
    @ApiOperation(value = "创建ProjectHook对象")
    @PostMapping
    public ResponseEntity<ProjectHook> create(
            @ApiParam(value = "项目ID", required = true)
            @RequestParam Integer projectId,
            @ApiParam(value = "userId")
            @RequestParam(required = false) Integer userId,
            @ApiParam(value = "projectHook对象", required = true)
            @RequestBody ProjectHook projectHook) {
        return Optional.ofNullable(hookService.createProjectHook(projectId, projectHook, userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new FeignException("error.projects.add.hook"));
    }


    /**
     * 更新ProjectHook对象
     *
     * @param projectId 项目id
     * @param userId    用户Id
     * @param hookId    hookId
     * @return ProjectHook
     */
    @ApiOperation(value = "更新ProjectHook对象")
    @PutMapping
    public ResponseEntity<ProjectHook> create(
            @ApiParam(value = "项目ID", required = true)
            @RequestParam Integer projectId,
            @ApiParam(value = "userId")
            @RequestParam(required = false) Integer userId,
            @ApiParam(value = "hookId", required = true)
            @RequestParam Integer hookId) {
        return Optional.ofNullable(hookService.updateProjectHook(projectId, hookId, userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.NO_CONTENT))
                .orElseThrow(() -> new FeignException("error.projects.update.hook"));
    }
}
