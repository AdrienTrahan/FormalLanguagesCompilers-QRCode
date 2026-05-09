export const prerender = true

export function GET() {
  return new Response(JSON.stringify({}), { 
    status: 200,
    headers: { 'Content-Type': 'application/json' }
  })
}