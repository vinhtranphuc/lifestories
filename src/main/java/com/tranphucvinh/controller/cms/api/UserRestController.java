package com.tranphucvinh.controller.cms.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tranphucvinh.common.BaseService;
import com.tranphucvinh.exception.BusinessException;
import com.tranphucvinh.payload.Response;
import com.tranphucvinh.security.UserPrincipal;
import com.tranphucvinh.service.UserService;

@RestController
@RequestMapping("/cms/api/user")
public class UserRestController {

    protected Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    UserService userService;

    @PostMapping("change-profile")
    public ResponseEntity<Response> changeProfile(@RequestBody Map<String, Object> params) {
        try {
            UserPrincipal userPrincipal = BaseService.getCurrentUser();
            params.put("user_id", userPrincipal.getId());
            userService.changeProfile(params, userPrincipal.getUsername());
            return ResponseEntity.ok().body(new Response(null, "Change profile successfully !"));
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response(null, e.getMessage()));
        } catch (Exception e) {
            logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("change-password")
    public ResponseEntity<Response> changePassword(@RequestBody Map<String, Object> params) {
        try {
            UserPrincipal userPrincipal = BaseService.getCurrentUser();
            params.put("user_id", userPrincipal.getId());
            params.put("crrPassword", userPrincipal.getPassword());
            userService.changePassword(params, userPrincipal.getUsername());
            return ResponseEntity.ok().body(new Response(null, "Change password successfully !"));
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response(null, e.getMessage()));
        } catch (Exception e) {
            logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PreAuthorize("hasRole('SUPPER_ADMIN')")
    @PostMapping("create-account")
    public ResponseEntity<Response> createAccount(@RequestBody Map<String, Object> params) {
        try {
            userService.createAccount(params);
            return ResponseEntity.ok().body(new Response(null, "Create account successfully !"));
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response(null, e.getMessage()));
        } catch (Exception e) {
            logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PreAuthorize("hasRole('SUPPER_ADMIN')")
    @PutMapping("edit-account")
    public ResponseEntity<Response> editAccount(@RequestBody Map<String, Object> params) {
        try {
            userService.editAccount(params);
            return ResponseEntity.ok().body(new Response(null, "Edit account successfully !"));
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response(null, e.getMessage()));
        } catch (Exception e) {
            logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PreAuthorize("hasRole('SUPPER_ADMIN')")
    @DeleteMapping("delete-users")
    public ResponseEntity<Response> deleteUsers(@RequestBody Map<String, Object> params) {
        try {
            userService.deleteUsers(params);
            return ResponseEntity.ok().body(new Response(null, "Delete user successfully !"));
        } catch (Exception e) {
            logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PreAuthorize("hasRole('SUPPER_ADMIN')")
    @GetMapping("list")
    public ResponseEntity<Response> userList() {
        try {
            List<Map<String, Object>> list = userService.getUserList();
            list.stream().map(t -> {
                String pattern = "MM-dd-yyyy HH:mm:ss";
                DateFormat df = new SimpleDateFormat(pattern);
                t.put("join_date", t.get("join_date") != null ? df.format(t.get("join_date")) : "");
                t.put("created_at", t.get("created_at") != null ? df.format(t.get("created_at")) : "");
                t.put("updated_at", t.get("updated_at") != null ? df.format(t.get("updated_at")) : "");
                t.put("enabled_val", (Boolean) t.get("enabled") == true ? "Yes" : "No");
                t.put("enabled_cd", (Boolean) t.get("enabled") == true ? "1" : "0");
                return t;
            }).collect(Collectors.toList());

            return ResponseEntity.ok().body(new Response(list, "Get user ok !"));
        } catch (Exception e) {
            logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
