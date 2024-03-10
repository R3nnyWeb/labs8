; LR_4_4_UART and Controller RST
	area stack, noinit, readwrite
	space 0x400	
stack_top	
	area reset, data, readonly
	area program,code,readonly
	dcd stack_top
	dcd start		
	entry		
start
	bl		pp1	
m
	bl 		pp2
;	bl 		PP3
	b	m
pp1				
	ldr	r1,=0x40020000; BazAdr_RST_CLK
	ldr r7,=0x08200050	; PER_
	str r7,[r1, #0x1c]	;_CLOCK 
	ldr r7,=0x1000000	; uart1_
	str r7,[r1, #0x28]	;_CLOC	
	ldr r2,=0x400a8000	;bazAdr_PortA
	mov	r7, #0xff		;
	str	r7, [r2,#0x04]	; OE
	str	r7, [r2,#0x0c]	;ANALOG
	movw  r7, #0xc000	;
	str	r7, [r2,#0x08]	; FUNC
	movw  r7, #0xaaaa	;	
	str	r7, [r2,#0x18]	;PWR 
	ldr	r3,=0x40030000	;BazAdrUart1
	mov r7, #0x01		;
	str r7,[r3, #0x24]	; IBRD	
	mov r7, #0x0		;
	str r7,[r3, #0x28]	;FBRD
	mov r7, #0x62		; 
	str r7,[r3,#0x2c ]	; LCR_H
	movw r7, #0x0101	;
	str r7,[r3, #0x30]	;CR 
	mov r4, #0x51		; data
	str r4,[r3]			; DR
	bx	lr
pp2	add r5, #1		; counter

	str r5,[r2]		;	
	and r6, r5, #0x07f
	eors r6, #0
	streq r4, [r3]
pp3 	
	bx lr
	END
