package com.gzlg.controller;

import com.gzlg.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 人脸识别控制器（转发到 Python Flask 服务）
 */
@RestController
@Slf4j
public class FaceController {

    private static final String FLASK_URL = "http://127.0.0.1:5000/recognize";

    @PostMapping("/api/face/recognize")
    public Result<String> recognize(@RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("图片不能为空");
        }
        try {
            // 构造 multipart 请求转发到 Flask
            RestTemplate restTemplate = new RestTemplate();
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", file.getResource());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(FLASK_URL, requestEntity, Map.class);
            Map<String, Object> result = response.getBody();

            if (result != null && Boolean.TRUE.equals(result.get("success"))) {
                String name = (String) result.get("name");
                log.info("人脸识别成功: {}", name);
                return Result.success(name);
            } else {
                String msg = result != null ? (String) result.get("message") : "识别失败";
                log.info("人脸识别失败: {}", msg);
                return Result.error(msg != null ? msg : "未找到匹配的人脸");
            }
        } catch (Exception e) {
            log.error("人脸识别异常", e);
            return Result.error("识别服务异常，请确认 Flask 服务已启动");
        }
    }
}
