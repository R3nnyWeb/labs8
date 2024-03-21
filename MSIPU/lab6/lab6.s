;LR_4_	SPI	1 
STACK_TOP EQU 0x00000100;2	 
     PRESERVE8			;3
     THUMB      		;4
     AREA RESET, CODE, READONLY;5
     DCD STACK_TOP 			;6
     DCD Start				;7
     ENTRY					;8

Start						;9
	ldr	r0,=0x40020000		;10
	ldr r3,=0x2AE00110	;11 PER_
	str r3,[r0, #0x1c]		;12_CLOCK 
	ldr r3,=0x1000004		;13 SSP_
	str r3,[r0, #0x2C]		;14_CLOCK

	ldr r0,=0x400a8000		;15 baz_adr_
	mov	r3, #0xff		;16_PortA
	str	r3, [r0,#0x04]		;17 OE
	str	r3, [r0,#0x0c]		;18 ANALOG
	movw  r3, #0x0000		;19
	str	r3, [r0,#0x08]		;20 FUNC
	movw  r3, #0xaaaa		;21	
	str	r3, [r0,#0x18]		;22PWR 
	ldr r1,=0x400e8000		;23 baz_adr_
	mov	r3, #0xff		;24_PortF
	str	r3, [r1,#0x04] 	;25 OE
	str	r3, [r1,#0x0c] 	;26 ANALOG
	movw  r3, #0x0aa		;27
	str	r3, [r1,#0x08] 	;28 FUNC 
	movw  r3, #0xaaaa		;29	
	str	r3, [r1,#0x18]		;30PWR

	ldr	r2,=0x40040000   ;31BazAdrSSP1
	movw r3, #0x07		;32 CR0
	str r3,[r2]			;33 
	mov r3, #0x02		;34
	str r3,[r2, #0x04] 		;35 CR1
	mov r3, #0x02		;36
	str r3,[r2, #0x10]		;37 ?PSR

m	add	r4, #0x01		;38 N(t)
	and	r4, #0x0f		;39
 	str r4,[r2,#0x08]		;40
	mov r10, #0xa0		;41 counter
tau	subs	r10, #1		;42
	str r10,[r0]			;43
	bpl tau			;44
	b m				;45
	END	
