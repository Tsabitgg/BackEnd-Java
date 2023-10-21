package com.binaracademy.challenge5.Controller;

import com.binaracademy.challenge5.Model.Merchant;
import com.binaracademy.challenge5.Model.Response.MerchantResponse;
import com.binaracademy.challenge5.Service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/merchant")
public class MerchantController {
    private final MerchantService merchantService;

    @Autowired
    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping(value = "/add-merchant", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MerchantResponse> addMerchant(@RequestBody Merchant merchant) {
        Merchant createdMerchant = merchantService.addMerchant(merchant);
        MerchantResponse merchantResponse = createMerchantResponse(createdMerchant);
        return new ResponseEntity<>(merchantResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{merchantCode}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MerchantResponse> updateMerchantStatus(@PathVariable Long merchantCode, @RequestParam boolean open) throws Exception {
        Merchant updatedMerchant = merchantService.updateMerchantStatus(merchantCode, open);
        MerchantResponse merchantResponse = createMerchantResponse(updatedMerchant);
        return new ResponseEntity<>(merchantResponse, HttpStatus.OK);
    }


    @GetMapping(value = "/get-all-merchant", produces = "application/json")
    public ResponseEntity<List<MerchantResponse>> getAllMerchants() {
        List<Merchant> merchants = merchantService.getAllMerchants();
        List<MerchantResponse> merchantResponses = merchants.stream()
                .map(this::createMerchantResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(merchantResponses, HttpStatus.OK);
    }

    @GetMapping(value = "/get-merchant-open", produces = "application/json")
    public ResponseEntity<List<MerchantResponse>> getOpenMerchants() {
        List<Merchant> openMerchants = merchantService.getOpenMerchants();
        List<MerchantResponse> openMerchantResponses = openMerchants.stream()
                .map(this::createMerchantResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(openMerchantResponses, HttpStatus.OK);
    }

    // membuat objek MerchantResponse dari entitas Merchant
    private MerchantResponse createMerchantResponse(Merchant merchant) {
        MerchantResponse merchantResponse = new MerchantResponse();
        merchantResponse.setMerchantCode(merchant.getMerchantCode());
        merchantResponse.setMerchantName(merchant.getMerchantName());
        merchantResponse.setMerchantLocation(merchant.getMerchantLocation());
        merchantResponse.setOpen(merchant.isOpen());
        return merchantResponse;
    }
}
